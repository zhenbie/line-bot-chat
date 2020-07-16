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
public class UserGetCouponLog {
    /**
     * 表主键
     */
    private Integer id;

    /**
     * LINE userId
     */
    private String userId;

    /**
     * 用户手机号
     */
    private String phoneNumber;

    /**
     * 
     */
    private String couponNo;

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