package com.gjx.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gjx.dao.QuestionDtoMapper;
import com.gjx.dto.QuestionDto;
import com.gjx.dto.QuestionDtoExample;
import com.gjx.service.IAnswerService;
import com.gjx.service.IQuestionService;
import com.gjx.service.ISmallService;
import com.gjx.util.RedisUtil;

@Service
public class QuestionServiceImpl implements IQuestionService {
    private final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ISmallService smallService;
    @Autowired
    private IAnswerService answerService;
    @Autowired
    private QuestionDtoMapper questionDtoMapper;

    @Override
    public String addQuestion(String message, HttpServletRequest request) {
        JSONObject parseObject = JSON.parseObject(message);
        String questionName = parseObject.getString("questionName");
        String questionId = parseObject.getString("questionId");
        String uuid = getUUID(request);
        String openIdAndSessionKey = smallService.getOpenIdAndSessionKey(uuid);

        QuestionDto questionDto = new QuestionDto();
        Date now = new Date();
//		String qustionId = redisUtil.getUUID();
        questionDto.setId(questionId);
        questionDto.setOpenId(openIdAndSessionKey.split(",")[0]);
        questionDto.setQuestionName(questionName);
        questionDto.setCreateTime(now);
        questionDto.setUpTime(now);
        insertQuestion(questionDto);
        log.info("插入问题表--" + questionName);
        return questionId;
    }

    private void insertQuestion(QuestionDto questionDto) {
        questionDtoMapper.insert(questionDto);
    }

    @Override
    public Map<Object, Object> queryQuestions(String uuid) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        String openIdAndSessionKey = smallService.getOpenIdAndSessionKey(uuid);
        String openId = openIdAndSessionKey.split(",")[0];
        // 获取问题列表
        List<QuestionDto> qustions = getQustions(openId);
        // 获取回复
        getAnswer(qustions, map);
        return map;
    }

    private void getAnswer(List<QuestionDto> qustions, Map<Object, Object> map) {
        if (qustions != null && qustions.size() > 0) {
            for (QuestionDto questionDto : qustions) {
                Map<String, Object> idMap = new HashMap<String, Object>();
                Map<String, Object> dataMap = new HashMap<String, Object>();
                String questionName = questionDto.getQuestionName();
                Date createTime = questionDto.getCreateTime();
                String questionId = questionDto.getId();
//				Map<String, List<String>> mapList = new HashMap<String, List<String>>();
                idMap.put("questionId", questionId);
                dataMap.put("title", questionName);
                dataMap.put("createTime", createTime);
//				List<AnswerDto> answerDtoList = answerService.querAnswerDto(questionId);
//				List<String> answerContentList = new ArrayList<String>();
//				if (null!=answerDtoList && answerDtoList.size()>0) {
//					for (AnswerDto answerDto : answerDtoList) {
//						String content = answerDto.getContent();
//						answerContentList.add(content);
//					}
//					mapList.put(questionName, answerContentList);
//				}
                map.put(idMap, dataMap);
            }
        }
    }

    private List<QuestionDto> getQustions(String openId) {
        QuestionDtoExample questionDtoExample = new QuestionDtoExample();
        questionDtoExample.createCriteria().andOpenIdEqualTo(openId);
        List<QuestionDto> qustions = questionDtoMapper.selectByExample(questionDtoExample);
        return qustions;
    }

    @Override
    public String getUUID(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals("sessionid")) {
                    String uuid = cookie.getValue();
                    return uuid;
                }
            }
        } else {
            log.info("未获取到sessionid");
        }
        return null;
    }
}
