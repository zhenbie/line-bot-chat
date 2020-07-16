package com.linecorp.bot.spring.boot.service;


import com.linecorp.bot.spring.boot.pojo.dto.UserShareInfoDto;
import com.linecorp.bot.spring.boot.pojo.entity.UserShareLog;

import java.util.List;

/**
 * @Description TODO
 * @Author zhenbaolei
 * @Date 2020/7/6 9:16
 * @Version 1.0
 */
public interface IUserShareLogService {

    /**
     * 保存用户分享日志
     *
     * @param userShareLog
     * @return
     */
    int saveUserShareLog(UserShareLog userShareLog);

    /**
     * 更新用户分享日志
     *
     * @param userShareLog
     * @return
     */
    int updateUserShareLog(UserShareLog userShareLog);

    /**
     * 根据userId查询分享日志
     *
     * @param userId
     * @return
     */
    List<UserShareLog> queryUserShareLogByUserId(String userId);

    /**
     * 根据分享人userId和分享口令查询分享日志
     *
     * @param fromUserId
     * @param shareUri
     * @return
     */
    List<UserShareLog> queryUserShareLogByUserIdAndShareUri(String fromUserId, String shareUri);

    /**
     * 根据分享人userId、分享路径、被分享人查询分享日志
     *
     * @param fromUserId
     * @param shareUri
     * @param toUserId
     * @return
     */
    List<UserShareLog> queryUserShareLogByUserIdAndShareUriAndToUserId(String fromUserId, String shareUri, String toUserId);

    /**
     * 根据分享路径、被分享人查询分享日志
     *
     * @param shareUri
     * @param toUserId
     * @return
     */
    List<UserShareLog> queryUserShareLogShareUriAndToUserId(String shareUri, String toUserId);

    /**
     * 根据分享人的userId获取分享日志
     *
     * @param fromUserId
     * @return
     */
    List<UserShareInfoDto> queryUserShareLogByFromUserId(String fromUserId);
}
