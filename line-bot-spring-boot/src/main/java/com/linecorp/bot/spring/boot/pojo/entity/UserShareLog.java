package com.linecorp.bot.spring.boot.pojo.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户分享日志
 *
 * @author Mybatis Generator
 * @date 2020-07-06
 */
@Data
public class UserShareLog {
    /**
     * 表主键
     */
    private Integer id;

    /**
     * from LINE userId
     */
    private String fromUserId;

    /**
     * to LINE id
     */
    private String toUserId;

    /**
     * 分享token
     */
    private String shareToken;

    /**
     * 分享uri
     */
    private String shareUri;

    /**
     * 分享状态，01-已分发，02-已点击，03-已转化
     */
    private String shareStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;
}