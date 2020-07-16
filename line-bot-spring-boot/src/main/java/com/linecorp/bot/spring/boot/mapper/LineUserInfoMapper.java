package com.linecorp.bot.spring.boot.mapper;

import com.linecorp.bot.spring.boot.pojo.entity.LineUserInfo;
import com.linecorp.bot.spring.boot.pojo.entity.LineUserInfoExample;

import java.util.List;

/**
 * Created by Mybatis Generator
 *
 * @author Mybatis Generator
 * @date 2020-07-03
 */
public interface LineUserInfoMapper {
    long countByExample(LineUserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LineUserInfo record);

    int insertSelective(LineUserInfo record);

    List<LineUserInfo> selectByExample(LineUserInfoExample example);

    LineUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LineUserInfo record);

    int updateByPrimaryKey(LineUserInfo record);
}