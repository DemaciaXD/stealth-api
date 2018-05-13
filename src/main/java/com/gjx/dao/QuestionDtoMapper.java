package com.gjx.dao;

import com.gjx.dto.QuestionDto;
import com.gjx.dto.QuestionDtoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuestionDtoMapper {
    int countByExample(QuestionDtoExample example);

    int deleteByExample(QuestionDtoExample example);

    int deleteByPrimaryKey(String id);

    int insert(QuestionDto record);

    int insertSelective(QuestionDto record);

    List<QuestionDto> selectByExample(QuestionDtoExample example);

    QuestionDto selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") QuestionDto record, @Param("example") QuestionDtoExample example);

    int updateByExample(@Param("record") QuestionDto record, @Param("example") QuestionDtoExample example);

    int updateByPrimaryKeySelective(QuestionDto record);

    int updateByPrimaryKey(QuestionDto record);
}