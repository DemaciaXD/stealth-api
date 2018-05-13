package com.gjx.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface IQuestionService {
	
	String addQuestion(String message, HttpServletRequest request);
	
	/**
	 * 获取问题列表
	 * @param uuid
	 * @return
	 */
	Map<Object, Object> queryQuestions(String uuid);

	String getUUID(HttpServletRequest request);
}
