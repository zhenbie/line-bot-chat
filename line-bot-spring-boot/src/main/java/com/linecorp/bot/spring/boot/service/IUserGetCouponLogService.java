package com.linecorp.bot.spring.boot.service;


import com.linecorp.bot.spring.boot.pojo.entity.UserGetCouponLog;

import java.util.List;

/**
 * @Description TODO
 * @Author zhenbaolei
 * @Date 2020/7/6 9:16
 * @Version 1.0
 */
public interface IUserGetCouponLogService {

    /**
     * 根据用户Id获取用户领取券日志数据
     *
     * @param userId
     * @return
     */
    List<UserGetCouponLog> queryUserGetCouponLogList(String userId);

    /**
     * 保存用户领取券日志
     *
     * @param userGetCouponLog
     * @return
     */
    int saveUserGetCouponLog(UserGetCouponLog userGetCouponLog);
}
