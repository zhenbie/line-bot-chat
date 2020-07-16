package com.linecorp.bot.spring.boot.mapper;


import com.linecorp.bot.spring.boot.pojo.entity.UserGetCouponLog;
import com.linecorp.bot.spring.boot.pojo.entity.UserGetCouponLogExample;

import java.util.List;

/**
 * Created by Mybatis Generator
 *
 * @author Mybatis Generator
 * @date 2020-07-03
 */
public interface UserGetCouponLogMapper {
    long countByExample(UserGetCouponLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserGetCouponLog record);

    int insertSelective(UserGetCouponLog record);

    List<UserGetCouponLog> selectByExample(UserGetCouponLogExample example);

    UserGetCouponLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserGetCouponLog record);

    int updateByPrimaryKey(UserGetCouponLog record);
}