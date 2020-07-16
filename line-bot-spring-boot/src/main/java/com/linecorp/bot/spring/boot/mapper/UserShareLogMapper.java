package com.linecorp.bot.spring.boot.mapper;

import com.linecorp.bot.spring.boot.pojo.entity.UserShareLog;
import com.linecorp.bot.spring.boot.pojo.entity.UserShareLogExample;

import java.util.List;

/**
 * Created by Mybatis Generator
 *
 * @author Mybatis Generator
 * @date 2020-07-03
 */
public interface UserShareLogMapper {
    long countByExample(UserShareLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserShareLog record);

    int insertSelective(UserShareLog record);

    List<UserShareLog> selectByExample(UserShareLogExample example);

    UserShareLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserShareLog record);

    int updateByPrimaryKey(UserShareLog record);
}