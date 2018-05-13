package com.gjx.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gjx.dto.AnswerDto;

public interface IAnswerService {
	void responseQuestion(String message, HttpServletRequest request);
	
	List<AnswerDto> querAnswerDto(String questionId);
}
