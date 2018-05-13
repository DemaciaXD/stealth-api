package com.gjx.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gjx.dao.AnswerDtoMapper;
import com.gjx.dto.AnswerDto;
import com.gjx.dto.AnswerDtoExample;
import com.gjx.service.IAnswerService;
import com.gjx.service.IQuestionService;
import com.gjx.service.ISmallService;
import com.gjx.util.RedisUtil;
@Service
public class AnswerServiceImpl implements IAnswerService {
	private final Logger log = LoggerFactory.getLogger(AnswerServiceImpl.class);
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private ISmallService smallService;
	@Autowired
	private AnswerDtoMapper answerDtoMapper;
	@Autowired
	private IQuestionService questionService;

	@Override
	public void responseQuestion(String message, HttpServletRequest request) {
		JSONObject parseObject = JSON.parseObject(message);
		String qustionId = parseObject.getString("qustionId");
		String content = parseObject.getString("content");
		String uuid = questionService.getUUID(request);
		
		String openIdAndSessionKey = smallService.getOpenIdAndSessionKey(uuid);
		AnswerDto answerDto = new AnswerDto();
		Date now = new Date();
		String answerId = redisUtil.getUUID();
		answerDto.setId(answerId);
		answerDto.setOpenId(openIdAndSessionKey.split(",")[0]);
		answerDto.setQuestionId(qustionId);
		answerDto.setContent(content);
		answerDto.setCreateTime(now);
		answerDto.setUpdateTime(now);
		insertAnswer(answerDto);
	}

	private void insertAnswer(AnswerDto answerDto) {
		answerDtoMapper.insert(answerDto);
	}

	@Override
	public List<AnswerDto> querAnswerDto(String questionId) {
		log.info(questionId);
		AnswerDtoExample answerDtoExample = new AnswerDtoExample();
		answerDtoExample.createCriteria().andQuestionIdEqualTo(questionId);
		return answerDtoMapper.selectByExample(answerDtoExample);
	}

}
