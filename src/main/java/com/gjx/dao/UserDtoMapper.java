package com.gjx.dao;

import com.gjx.dto.UserDto;
import com.gjx.dto.UserDtoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserDtoMapper {
    int countByExample(UserDtoExample example);

    int deleteByExample(UserDtoExample example);

    int deleteByPrimaryKey(String openId);

    int insert(UserDto record);

    int insertSelective(UserDto record);

    List<UserDto> selectByExample(UserDtoExample example);

    UserDto selectByPrimaryKey(String openId);

    int updateByExampleSelective(@Param("record") UserDto record, @Param("example") UserDtoExample example);

    int updateByExample(@Param("record") UserDto record, @Param("example") UserDtoExample example);

    int updateByPrimaryKeySelective(UserDto record);

    int updateByPrimaryKey(UserDto record);
}