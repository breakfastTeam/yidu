package com.smartbean.mobile.controller;

import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.enums.OauthScope;
import com.smartbean.util.weixin.WeixinConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2015/5/19.
 */
@Controller
@RequestMapping("/")
public class WeixinAuthController {

    @RequestMapping(value = {"/jumpToAuth"})
    public String jumpToAuth(@RequestParam String appId, @RequestParam String appSecret, @RequestParam String redirectUrl){
        WeixinConfig config = new WeixinConfig();
        OauthAPI oauthAPI = config.getOauthAPI(appId, appSecret);
        String jumpUrl = oauthAPI.getOauthPageUrl(redirectUrl, OauthScope.SNSAPI_BASE, "");
        return"redirect:"+jumpUrl;
    }

}
