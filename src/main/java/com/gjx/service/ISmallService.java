package com.gjx.service;

import javax.servlet.http.HttpServletResponse;


public interface ISmallService {
	
	String login(String message);
	
	/**
	 * 1. 获取openId, sessionKey
	 * 2. 生成 uuid
	 * 3. redis存储 uuid - openId,sessionKey
	 * 4. 返回uuid给前台
	 * @param jsCode
	 * @return
	 */
	String getUUID(String jsCode);
	
	/**
	 * 解密encryptedData存表
	 * @param encryptedData
	 * @param session_key
	 * @param iv
	 */
	void decEncryptedData(String encryptedData, String session_key, String iv);
	
	String getOpenIdAndSessionKey(String uuid);

	void addCookie(String uuid, HttpServletResponse response);
}
