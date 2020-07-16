package com.linecorp.bot.spring.boot.controller;

import com.alibaba.fastjson.JSON;
import com.linecorp.bot.spring.boot.infra.line.api.v2.LineAPIService;
import com.linecorp.bot.spring.boot.infra.line.api.v2.response.AccessToken;
import com.linecorp.bot.spring.boot.infra.line.api.v2.response.IdToken;
import com.linecorp.bot.spring.boot.pojo.dto.UserShareInfoDto;
import com.linecorp.bot.spring.boot.pojo.entity.LineUserInfo;
import com.linecorp.bot.spring.boot.pojo.entity.UserShareLog;
import com.linecorp.bot.spring.boot.service.ILineUserInfoService;
import com.linecorp.bot.spring.boot.service.IUserShareLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author zhenbaolei
 * @Date 2020/7/6 9:57
 * @Version 1.0
 */
@Slf4j
@Controller
public class UserShareLogController {

    private static final String LINE_WEB_LOGIN_STATE = "lineWebLoginState";
    static final String ACCESS_TOKEN = "accessToken";
    private static final String NONCE = "nonce";
    private static final String ACVIVITY_URI = "/activity/9298400434";

    @Autowired
    private LineAPIService lineAPIService;

    @Autowired
    private IUserShareLogService userShareLogService;

    @Autowired
    private ILineUserInfoService lineUserInfoService;

    @RequestMapping("/activity/{fromUserId}/{shareToken}")
    public String activity(HttpSession httpSession, @PathVariable("fromUserId") String fromUserId, @PathVariable("shareToken") String shareToken) {
        log.info("from userId: {}, shareToken:{}", fromUserId, shareToken);
        httpSession.setAttribute("fromUserId", fromUserId);
        httpSession.setAttribute("shareToken", shareToken);
        return "redirect:/gotoauthpage";
    }

    /**
     * <p>Redirect to LINE Login Page</p>
     */
//    @RequestMapping(value = "/gotoauth")
//    public String goToAuthPage(HttpSession httpSession,
//                               @PathVariable("fromUserId") String fromUserId,
//                               @PathVariable("shareToken") String shareToken){
//        final String state = CommonUtils.getToken();
//        final String nonce = CommonUtils.getToken();
//        httpSession.setAttribute(LINE_WEB_LOGIN_STATE, state);
//        httpSession.setAttribute(NONCE, nonce);
//        final String url = lineAPIService.getLineWebLoginUrl(state, nonce, Arrays.asList("openid", "profile", "email"));
//        return "redirect:" + url;
//    }

    /**
     * <p>login success Page
     */
    @RequestMapping("/toActivityDetail/{fromUserId}/{shareToken}")
    public String success(HttpSession httpSession, Model model,
                          @PathVariable("fromUserId") String fromUserId,
                          @PathVariable("shareToken") String shareToken) {
        log.info("activityDetail:/auth/{}/{}", fromUserId, shareToken);
        AccessToken token = (AccessToken) httpSession.getAttribute(ACCESS_TOKEN);
        log.info("id_token:{}", token.id_token);
        if (token == null) {
            return "redirect:/";
        }

        if (!lineAPIService.verifyIdToken(token.id_token, (String) httpSession.getAttribute(NONCE))) {
            // verify failed
            return "redirect:/";
        }

        httpSession.removeAttribute(NONCE);
        IdToken idToken = lineAPIService.idToken(token.id_token);
        if (log.isDebugEnabled()) {
            log.debug("userId : " + idToken.sub);
            log.debug("displayName : " + idToken.name);
            log.debug("pictureUrl : " + idToken.picture);
        }

        //保存或更新用户信息
        String toUserId = idToken.sub;
        LineUserInfo lineUserInfo = lineUserInfoService.getLineUserInfoByUserId(toUserId);
        if (null == lineUserInfo) {
            lineUserInfo = new LineUserInfo();
            lineUserInfo.setUserId(toUserId);
            lineUserInfo.setDisplayName(idToken.name);
            lineUserInfo.setPictureUrl(idToken.picture);
            lineUserInfoService.saveLineUserInfo(lineUserInfo);
            log.info("Line 用户信息保存成功，{}", lineUserInfo);
        } else {
            lineUserInfo.setDisplayName(idToken.name);
            lineUserInfo.setPictureUrl(idToken.picture);
            lineUserInfoService.updateLineUserInfo(lineUserInfo);
            log.info("Line 用户信息更新成功，{}", lineUserInfo);
        }
        model.addAttribute("lineUserInfo", lineUserInfo);

        if (!fromUserId.equals(toUserId)) {
            List<UserShareLog> userShareLogList = userShareLogService.queryUserShareLogByUserIdAndShareUriAndToUserId(fromUserId, ACVIVITY_URI, toUserId);
            if (userShareLogList.size() > 0) {
                UserShareLog userShareLog = userShareLogList.get(0);
                userShareLog.setFromUserId(fromUserId);
                userShareLog.setToUserId(toUserId);
                userShareLog.setUpdateTime(new Date());
                userShareLogService.updateUserShareLog(userShareLog);
                log.info("更新分享人日志：{}", JSON.toJSONString(userShareLog));
            } else {
                UserShareLog userShareLog = new UserShareLog();
                userShareLog.setFromUserId(fromUserId);
                userShareLog.setToUserId(toUserId);
                userShareLog.setShareToken(shareToken);
                userShareLog.setShareUri(ACVIVITY_URI);
                userShareLog.setCreateTime(new Date());
                userShareLog.setShareStatus("02");
                userShareLogService.saveUserShareLog(userShareLog);
                log.info("保存分享人日志：{}", JSON.toJSONString(userShareLog));
            }
        }

        //获取当前用户的分享日志列表
        List<UserShareInfoDto> userShareInfoDtoList = userShareLogService.queryUserShareLogByFromUserId(toUserId);
        model.addAttribute("userShareInfoList", userShareInfoDtoList);
        log.info("userShareInfoList：{}", JSON.toJSONString(userShareInfoDtoList));
        return "merchant/activity";
    }


}
