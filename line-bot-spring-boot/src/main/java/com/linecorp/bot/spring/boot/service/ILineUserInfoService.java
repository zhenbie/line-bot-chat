package com.linecorp.bot.spring.boot.service;


import com.linecorp.bot.spring.boot.pojo.entity.LineUserInfo;

/**
 * @Description TODO
 * @Author zhenbaolei
 * @Date 2020/7/6 9:16
 * @Version 1.0
 */
public interface ILineUserInfoService {

    /**
     * 保存Line用户信息
     *
     * @param lineUserInfo
     * @return
     */
    int saveLineUserInfo(LineUserInfo lineUserInfo);

    /**
     * 更新Line用户信息
     *
     * @param lineUserInfo
     * @return
     */
    int updateLineUserInfo(LineUserInfo lineUserInfo);

    /**
     * 根据用户Id获取Line用户信息
     *
     * @param userId
     * @return
     */
    LineUserInfo getLineUserInfoByUserId(String userId);
}
