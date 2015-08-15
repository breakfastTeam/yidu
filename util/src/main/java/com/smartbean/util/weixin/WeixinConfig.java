package com.smartbean.util.weixin;

import com.github.sd4324530.fastweixin.api.*;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;

/**
 * Created by Administrator on 2015/6/24.
 */
public class WeixinConfig {
    public ApiConfig getApiConfig(String appId, String appSecret) {
        return new ApiConfig(appId, appSecret);
    }
    public UserAPI getUserAPI(String appId, String appSecret) {
        return new UserAPI(this.getApiConfig(appId, appSecret));
    }
    public OauthAPI getOauthAPI(String appId, String appSecret) {
        return new OauthAPI(this.getApiConfig(appId, appSecret));
    }

    public CustomAPI getCustomAPI(String appId, String appSecret){
        return new CustomAPI(this.getApiConfig(appId, appSecret));
    }

    public MenuAPI getMenuAPI(String appId, String appSecret){
        return new MenuAPI(this.getApiConfig(appId, appSecret));
    }

    public MaterialAPI getMaterialAPI(String appId, String appSecret){
        return new MaterialAPI(this.getApiConfig(appId, appSecret));
    }

    public MessageAPI getMessageAPI(String appId, String appSecret){
        return new MessageAPI(this.getApiConfig(appId, appSecret));
    }

    public MediaAPI getMediaAPI(String appId, String appSecret){
        return new MediaAPI(this.getApiConfig(appId, appSecret));
    }

    public JsAPI getJsAPI(String appId, String appSecret){
        ApiConfig apiConfig = this.getApiConfig(appId, appSecret);
        apiConfig.setEnableJsApi(true);
        return new JsAPI(apiConfig);
    }
}
