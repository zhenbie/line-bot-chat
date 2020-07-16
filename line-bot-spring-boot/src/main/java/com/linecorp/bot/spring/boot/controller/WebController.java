/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.linecorp.bot.spring.boot.controller;

import com.alibaba.fastjson.JSON;
import com.linecorp.bot.spring.boot.infra.line.api.v2.LineAPIService;
import com.linecorp.bot.spring.boot.infra.line.api.v2.response.AccessToken;
import com.linecorp.bot.spring.boot.infra.line.api.v2.response.IdToken;
import com.linecorp.bot.spring.boot.infra.utils.CommonUtils;
import com.linecorp.bot.spring.boot.pojo.dto.UserShareInfoDto;
import com.linecorp.bot.spring.boot.pojo.entity.LineUserInfo;
import com.linecorp.bot.spring.boot.service.ILineUserInfoService;
import com.linecorp.bot.spring.boot.service.IUserShareLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * <p>user web application pages</p>
 */
@Slf4j
@Controller
public class WebController {

    private static final String LINE_WEB_LOGIN_STATE = "lineWebLoginState";
    static final String ACCESS_TOKEN = "accessToken";
    private static final String NONCE = "nonce";

    @Autowired
    private LineAPIService lineAPIService;

    @Autowired
    private ILineUserInfoService lineUserInfoService;

    @Autowired
    private IUserShareLogService userShareLogService;

    /**
     * <p>LINE Login Button Page
     * <p>Login Type is to log in on any desktop or mobile website
     */
    @RequestMapping("/")
    public String login() {
        return "merchant/merchantDetail";
    }

    /**
     * <p>Redirect to LINE Login Page</p>
     */
    @RequestMapping(value = "/gotoauthpage")
    public String goToAuthPage(HttpSession httpSession){
        final String state = CommonUtils.getToken();
        final String nonce = CommonUtils.getToken();
        httpSession.setAttribute(LINE_WEB_LOGIN_STATE, state);
        httpSession.setAttribute(NONCE, nonce);
        final String url = lineAPIService.getLineWebLoginUrl(state, nonce, Arrays.asList("openid", "profile", "email"));
        return "redirect:" + url;
    }

    /**
     * <p>Redirect Page from LINE Platform</p>
     * <p>Login Type is to log in on any desktop or mobile website
     */
    @RequestMapping("/auth")
    public String auth(
            HttpSession httpSession,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "errorCode", required = false) String errorCode,
            @RequestParam(value = "errorMessage", required = false) String errorMessage) {
        String fromUserId = (String) httpSession.getAttribute("fromUserId");
        String shareToken = (String) httpSession.getAttribute("shareToken");
        if (!StringUtils.isEmpty(fromUserId)) {
            log.info("fromUserId:{}", fromUserId);
        }
        if (!StringUtils.isEmpty(shareToken)) {
            log.info("shareToken:{}", shareToken);
        }
        log.info("auth:{}", code);
        if (log.isDebugEnabled()) {
            log.debug("parameter code : " + code);
            log.debug("parameter state : " + state);
            log.debug("parameter scope : " + scope);
            log.debug("parameter error : " + error);
            log.debug("parameter errorCode : " + errorCode);
            log.debug("parameter errorMessage : " + errorMessage);
        }

        if (error != null || errorCode != null || errorMessage != null){
            return "redirect:/loginCancel";
        }

        if (!state.equals(httpSession.getAttribute(LINE_WEB_LOGIN_STATE))){
            return "redirect:/sessionError";
        }

        httpSession.removeAttribute(LINE_WEB_LOGIN_STATE);
        AccessToken token = lineAPIService.accessToken(code);
        if (log.isDebugEnabled()) {
            log.debug("scope : " + token.scope);
            log.debug("access_token : " + token.access_token);
            log.debug("token_type : " + token.token_type);
            log.debug("expires_in : " + token.expires_in);
            log.debug("refresh_token : " + token.refresh_token);
            log.debug("id_token : " + token.id_token);
        }
        httpSession.setAttribute(ACCESS_TOKEN, token);


        if (!StringUtils.isEmpty(fromUserId) && !StringUtils.isEmpty(shareToken)) {
            return "redirect:/toActivityDetail/" + fromUserId + "/" + shareToken;
        }
        return "redirect:/success";
    }

    /**
    * <p>login success Page
    */
    @RequestMapping("/success")
    public String success(HttpSession httpSession, Model model) {

        AccessToken token = (AccessToken)httpSession.getAttribute(ACCESS_TOKEN);
        if (token == null){
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
        String currUserId = idToken.sub;
        LineUserInfo lineUserInfo = lineUserInfoService.getLineUserInfoByUserId(currUserId);
        if (null == lineUserInfo) {
            lineUserInfo = new LineUserInfo();
            lineUserInfo.setUserId(currUserId);
            lineUserInfo.setDisplayName(idToken.name);
            lineUserInfo.setPictureUrl(idToken.picture);
            lineUserInfo.setPhoneNumber("");
            lineUserInfoService.saveLineUserInfo(lineUserInfo);
            log.info("Line 用户信息保存成功。");
        } else {
            lineUserInfo.setDisplayName(idToken.name);
            lineUserInfo.setPictureUrl(idToken.picture);
            lineUserInfoService.updateLineUserInfo(lineUserInfo);
            log.info("Line 用户信息更新成功。{}", JSON.toJSONString(lineUserInfo));
        }
        model.addAttribute("lineUserInfo", lineUserInfo);

        //获取当前用户的分享日志列表
        List<UserShareInfoDto> userShareInfoDtoList = userShareLogService.queryUserShareLogByFromUserId(currUserId);
        model.addAttribute("userShareInfoList", userShareInfoDtoList);
        log.info("userShareInfoList：{}", JSON.toJSONString(userShareInfoDtoList));
        return "merchant/activity";
    }

    /**
    * <p>login Cancel Page
    */
    @RequestMapping("/loginCancel")
    public String loginCancel() {
        return "user/login_cancel";
    }

    /**
    * <p>Session Error Page
    */
    @RequestMapping("/sessionError")
    public String sessionError() {
        return "user/session_error";
    }

}
