package com.gjx.dao;

import com.gjx.dto.AnswerDto;
import com.gjx.dto.AnswerDtoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnswerDtoMapper {
    int countByExample(AnswerDtoExample example);

    int deleteByExample(AnswerDtoExample example);

    int deleteByPrimaryKey(String id);

    int insert(AnswerDto record);

    int insertSelective(AnswerDto record);

    List<AnswerDto> selectByExample(AnswerDtoExample example);

    AnswerDto selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AnswerDto record, @Param("example") AnswerDtoExample example);

    int updateByExample(@Param("record") AnswerDto record, @Param("example") AnswerDtoExample example);

    int updateByPrimaryKeySelective(AnswerDto record);

    int updateByPrimaryKey(AnswerDto record);
}