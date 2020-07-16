package com.linecorp.bot.spring.boot.pojo.dto;

import lombok.Data;

/**
 * @Description TODO
 * @Author zhenbaolei
 * @Date 2020/7/6 12:53
 * @Version 1.0
 */
@Data
public class UserShareInfoDto {

    /**
     * from LINE userId
     */
    private String fromUserId;

    /**
     * to LINE id
     */
    private String toUserId;

    /**
     * 展示名称
     */
    private String displayName;

    /**
     * 用户头像url
     */
    private String pictureUrl;

    /**
     * 分享状态，01-已分发，02-已点击，03-已转化
     */
    private String shareStatus;

    /**
     * 创建时间
     */
    private String createTime;
}
