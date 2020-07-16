package com.linecorp.bot.spring.boot.controller;

import com.alibaba.fastjson.JSON;
import com.linecorp.bot.spring.boot.common.bean.ResponseVo;
import com.linecorp.bot.spring.boot.pojo.entity.LineUserInfo;
import com.linecorp.bot.spring.boot.pojo.entity.UserGetCouponLog;
import com.linecorp.bot.spring.boot.pojo.entity.UserShareLog;
import com.linecorp.bot.spring.boot.service.ILineUserInfoService;
import com.linecorp.bot.spring.boot.service.IUserGetCouponLogService;
import com.linecorp.bot.spring.boot.service.IUserShareLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author zhenbaolei
 * @Date 2020/7/6 15:49
 * @Version 1.0
 */
@Slf4j
@Controller
public class UserGetCouponController {

    private static final String ACVIVITY_URI = "/activity/9298400434";

    @Autowired
    private IUserShareLogService userShareLogService;

    @Autowired
    private ILineUserInfoService lineUserInfoService;

    @Autowired
    private IUserGetCouponLogService userGetCouponLogService;

    @GetMapping("/getCoupon/{toUserId}/{phoneNumber}")
    @ResponseBody
    public ResponseVo getCoupon(Model model, @PathVariable("toUserId") String toUserId,
                                @PathVariable("phoneNumber") String phoneNumber) {
        ResponseVo responseVo = new ResponseVo();
        List<UserShareLog> userShareLogList = userShareLogService.queryUserShareLogShareUriAndToUserId(ACVIVITY_URI, toUserId);
        if (userShareLogList.size() > 0) {
            log.info("查询到用户分享日志：{}", JSON.toJSONString(userShareLogList));
            UserShareLog userShareLog = userShareLogList.get(0);
            userShareLog.setUpdateTime(new Date());
            userShareLog.setShareStatus("03");
            userShareLogService.updateUserShareLog(userShareLog);
            log.info("更新分享人日志：{}", JSON.toJSONString(userShareLog));
        }
        userShareLogList = userShareLogService.queryUserShareLogShareUriAndToUserId(ACVIVITY_URI, toUserId);
        model.addAttribute("userShareLogList", userShareLogList);

        LineUserInfo lineUserInfo = lineUserInfoService.getLineUserInfoByUserId(toUserId);
        lineUserInfo.setPhoneNumber(phoneNumber);
        lineUserInfo.setUpdateTime(new Date());
        lineUserInfoService.updateLineUserInfo(lineUserInfo);
        log.info("更新用户信息");

        UserGetCouponLog userGetCouponLog = new UserGetCouponLog();
        userGetCouponLog.setUserId(toUserId);
        userGetCouponLog.setPhoneNumber(phoneNumber);
        userGetCouponLog.setCouponNo(String.valueOf(System.currentTimeMillis()-1300000000000L));
        userGetCouponLog.setCreateTime(new Date());
        userGetCouponLogService.saveUserGetCouponLog(userGetCouponLog);
        log.info("用户领取券成功：{}", JSON.toJSONString(userGetCouponLog));
        responseVo.setCode("success");
        responseVo.setMsg("领取成功");
        return responseVo;
    }
}
