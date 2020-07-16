package com.linecorp.bot.spring.boot.service.impl;

import com.linecorp.bot.spring.boot.mapper.UserGetCouponLogMapper;
import com.linecorp.bot.spring.boot.pojo.entity.UserGetCouponLog;
import com.linecorp.bot.spring.boot.pojo.entity.UserGetCouponLogExample;
import com.linecorp.bot.spring.boot.service.IUserGetCouponLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author zhenbaolei
 * @Date 2020/7/6 9:25
 * @Version 1.0
 */
@Service
public class UserGetCouponLogServiceImpl implements IUserGetCouponLogService {

    @Autowired
    private UserGetCouponLogMapper userGetCouponLogMapper;

    @Override
    public List<UserGetCouponLog> queryUserGetCouponLogList(String userId) {
        UserGetCouponLogExample example = new UserGetCouponLogExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return userGetCouponLogMapper.selectByExample(example);
    }

    @Override
    public int saveUserGetCouponLog(UserGetCouponLog userGetCouponLog) {
        return userGetCouponLogMapper.insert(userGetCouponLog);
    }
}
