package com.gjx.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gjx.config.MyConfig;
import com.gjx.dao.UserDtoMapper;
import com.gjx.dto.UserDto;
import com.gjx.service.ISmallService;
import com.gjx.util.HttpUtil;
import com.gjx.util.RedisUtil;
import com.gjx.util.WXDecUtil;
@Service
public class SmallServiceImpl implements ISmallService {
	private final Logger log = LoggerFactory.getLogger(SmallServiceImpl.class);
	private final Long LIFETIME = 604800L;
	private final String DECSUCCESS = "1";
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private UserDtoMapper userDtoMapper;
	@Autowired
	private MyConfig myConfig;
	
	@Override
	public String login(String message) {
		JSONObject parseObject = JSON.parseObject(message);
		String jsCode = parseObject.getString("js_code");
		String encryptedData = parseObject.getString("encryptedData");
		String iv = parseObject.getString("iv");
		
		// 获取sessionId, openId放redis并返回uuid
		String uuid = getUUID(jsCode);
		// 解密数据存表
		decEncryptedData(encryptedData, uuid, iv);
		return uuid;
	}

	private UserDto getUserByOpenId(String openId) {
		return userDtoMapper.selectByPrimaryKey(openId);
	}

	private boolean hasLogined(String openId) {
		Boolean result = Boolean.FALSE;
		if (getUserByOpenId(openId)!=null) {
			result = Boolean.TRUE;
			return result;
		}
		return false;
	}

	@Override
	public String getUUID(String jsCode) {
		String uuid = "";
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		StringBuffer buffer = new StringBuffer(url);
		buffer.append("?appid="+myConfig.getAppid());
		buffer.append("&secret="+myConfig.getSecret());
		buffer.append("&grant_type="+myConfig.getGrant_type());
		buffer.append("&js_code="+jsCode);
		try {
			String result = HttpUtil.get(buffer.toString());
			JSONObject jsonObject = JSON.parseObject(result);
			String sessionKey = jsonObject.getString("session_key");
			String openId = jsonObject.getString("openid");
			if (null!=sessionKey && null!=openId) {
				uuid = redisUtil.getUUID();
				redisUtil.set(uuid, openId + "," + sessionKey, 180L);// 存储3min
			}else {
				log.info("请求微信端获取session_key,openid  出错");
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("获取openId出错");
		}
		return uuid;
	}

	@Override
	public void decEncryptedData(String encryptedData, String uuid, String iv) {
		String session_key = "";
		String openIdAndSessionKey = getOpenIdAndSessionKey(uuid);
		if (null!=openIdAndSessionKey && !"".equals(openIdAndSessionKey)) {
			session_key = openIdAndSessionKey.split(",")[1];
			Map<String, Object> map = WXDecUtil.dec(encryptedData, session_key, iv);
			String status = (String) map.get("status");
			String userInfo = (String) map.get("userInfo");
			if (DECSUCCESS.equals(status)) {// 解密成功, 更新/存表
				UserDto userDto = dealUserData(userInfo);
				if (hasLogined(userDto.getOpenId())) {
					updateUserDto(userDto);
				}else {
					insertUser(userDto);
				}
			}else {// 解密失败
				log.info("解密失败");
			}
		}else {
			log.info("rides中获取openId失败");
		}
	}

	private void updateUserDto(UserDto userDto) {
		Date now = new Date();
		userDto.setUpTime(now);
		userDtoMapper.updateByPrimaryKeySelective(userDto);
	}
	
	private void insertUser(UserDto userDto) {
		log.info("用户[{}] 插入user表", userDto.toString());
		Date now = new Date();
		userDto.setCreateTime(now);
		userDto.setUpTime(now);
		userDtoMapper.insert(userDto);
		log.info("用户[{}] 插入user表完成=", userDto.getOpenId());
	}

	private UserDto dealUserData(String userInfo) {
		JSONObject parseObject = JSON.parseObject(userInfo);
		String openId = parseObject.getString("openId");
		String nickName = parseObject.getString("nickName");
		String gender = parseObject.getString("gender");
		String city = parseObject.getString("city");
		String province = parseObject.getString("province");
		String country = parseObject.getString("country");
		String avatarUrl = parseObject.getString("avatarUrl");
		String unionId = parseObject.getString("unionId");
		
		UserDto userDto = new UserDto();
		userDto.setOpenId(openId);
		userDto.setNickname(nickName);
		userDto.setGender(gender);
		userDto.setCity(city);
		userDto.setProvince(province);
		userDto.setCountry(country);
		userDto.setAvatarUrl(avatarUrl);
		userDto.setUnionId(unionId);
		return userDto;
	}

	@Override
	public String getOpenIdAndSessionKey(String uuid) {
		return redisUtil.get(uuid);
	}

	@Override
	public void addCookie(String uuid, HttpServletResponse response) {
		Cookie cookie = new Cookie("sessionid", uuid);
		response.addCookie(cookie);
	}

}
