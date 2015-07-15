package com.smartbean.mobile.controller;

import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.message.Article;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.NewsMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;
import com.github.sd4324530.fastweixin.message.req.MenuEvent;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.google.common.collect.Lists;
import com.smartbean.common.CustomerStatus;
import com.smartbean.entity.Customer;
import com.smartbean.service.CustomerService;
import com.smartbean.util.internet.Spider;
import com.smartbean.util.weixin.WeixinConfig;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * Created by Ez丶kkk on 15/4/16.
 */

@Controller
@RequestMapping("/weixin")
public class WeixinController extends WeixinControllerSupport {

    @Value("#{configProperties['host']}")
    private String host;
    @Value("#{configProperties['appid']}")
    private String appId;
    @Value("#{configProperties['appsecret']}")
    private String appSecret;
    @Value("#{configProperties['token']}")
    private String token;



    @Autowired
    private CustomerService customerService;

    Logger logger = LoggerFactory.getLogger(WeixinController.class);



    /**
     * 点击菜单出发的函数
     * **/
    @Override
    protected BaseMsg handleMenuClickEvent(MenuEvent event) {
        String key = event.getEventKey();
        return new TextMsg("菜单key："+key);
    }


    /**
     * 关注微信号触发的函数
     * **/
    @Override
    protected BaseMsg handleSubscribe(BaseEvent event) {

        WeixinConfig config = new WeixinConfig();
        UserAPI userAPI = config.getUserAPI(appId, appSecret);
        GetUserInfoResponse userInfo = userAPI.getUserInfo(event.getFromUserName());

        Customer customer = customerService.findByOpenId(event.getFromUserName());
        if(customer == null){
            customer = new Customer();
        }

        customer.setOpenId(event.getFromUserName());
        customer.setNickName(userInfo.getNickname());
        customer.setCity(userInfo.getCity());
        customer.setGender(userInfo.getSex());
        customer.setAvatar(userInfo.getHeadimgurl());
        customer.setCountry(userInfo.getCountry());
        customer.setGroupId(userInfo.getGroupid());
        customer.setLanguage(userInfo.getLanguage());
        customer.setProvince(userInfo.getProvince());
        customer.setCreateTime(DateTime.now());
        DateTime dateTime = new DateTime(userInfo.getSubscribeTime());
        customer.setSubscribeTime(dateTime);
        customer.setStatus(CustomerStatus.FOLLOW.toString());
        customerService.save(customer);

        String img = host + "/upload/intro.jpg";
        Article article = new Article();
        article.setPicUrl(img);
        article.setTitle("欢迎关注我们的微信号");

        List<Article> articles = Lists.newArrayList(article);

        return new NewsMsg(articles);

    }

    /**
     * 回复文本消息触发的函数
     * **/
    protected BaseMsg handleTextMsg(TextReqMsg msg) {
        Spider spider = new Spider();
        List<String[]> list = spider.searchWechatFromSogou(msg.getContent());
        List<Article> articles = Lists.newArrayList();

        for(String[] result : list){
            Article article = new Article();
            article.setPicUrl(result[0]);
            article.setTitle(result[2]);
            articles.add(article);
        }
        return new NewsMsg(articles);
    }


    @Override
    protected String getToken() {
        return token;
    }
}
