package com.linecorp.bot.spring.boot.pojo.entity;

import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author Mybatis Generator
 * @date 2020-07-03
 */
@Data
public class LineUserInfo {
    /**
     * 表主键
     */
    private Integer id;

    /**
     * line的userId
     */
    private String userId;

    /**
     * 展示名称
     */
    private String displayName;

    /**
     * 用户头像url
     */
    private String pictureUrl;

    /**
     * 用户状态消息
     */
    private String statusMessage;

    /**
     * 用户邮箱
     */
    private String emailAddr;

    /**
     * 用户手机号
     */
    private String phoneNumber;

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