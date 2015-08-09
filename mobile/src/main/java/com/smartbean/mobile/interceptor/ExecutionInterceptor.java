package com.smartbean.mobile.interceptor;

import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.smartbean.common.SessionData;
import com.smartbean.entity.Customer;
import com.smartbean.service.CustomerService;
import com.smartbean.util.weixin.WeixinConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by kkk on .
 */

public class ExecutionInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger(ExecutionInterceptor.class);

    @Value("#{configProperties['appid']}")
    private String appId;
    @Value("#{configProperties['appsecret']}")
    private String appSecret;

    @Autowired
    private CustomerService customerService;

    @Override
    /**
     * toFix: appId appSecret => md5
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest()
                .getSession();
        String code = request.getParameter("code");
        String uri = request.getRequestURI();

//        if(!uri.contains("jumpToAuth")){
//            if(StringUtils.isNotBlank(code)){
//                WeixinConfig config = new WeixinConfig();
//                OauthAPI oauthAPI = config.getOauthAPI(appId, appSecret);
//                OauthGetTokenResponse rs = oauthAPI.getToken(code);
//                String openId = rs.getOpenid();
//                session.setAttribute(SessionData.OPEN_ID.toString(), openId);
//                Customer customer = customerService.findByOpenId(openId);
//                session.setAttribute(SessionData.CUSTOMER_ID.toString(), customer.getId());
//            }
//
//            String openId = (String)session.getAttribute(SessionData.OPEN_ID.toString());
//            if(StringUtils.isBlank(openId) && StringUtils.isBlank(code)){
//                request.getRequestDispatcher("/jumpToAuth?appId="+appId+"&appSecret="+appSecret+"&redirectUrl="+request.getRequestURL().toString()).forward(request, response);
//                return false;
//            }else {
//                return true;
//            }
//        }else{
//            return true;
//        }
        session.setAttribute(SessionData.CUSTOMER_ID.toString(), "2c9ba3814e6bd285014e6bd37615000f");
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
    }
}
