package com.smartbean.mobile.controller;

import com.smartbean.common.SessionData;
import com.smartbean.entity.Article;
import com.smartbean.entity.Subscribe;
import com.smartbean.entity.Wechat;
import com.smartbean.entity.WechatType;
import com.smartbean.service.ArticleService;
import com.smartbean.service.SubscribeService;
import com.smartbean.service.WechatService;
import com.smartbean.service.WechatTypeService;
import com.smartbean.util.fastjson.FastJson;
import com.smartbean.util.fastjson.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2015/6/25.
 */
@Controller
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    /**
     * 订阅
     * */
    @ResponseBody
    @RequestMapping(value = {"/subscribe"})
    public JsonResult subscribe(@RequestParam String wechatId, HttpSession session){
        String customerId = (String)session.getAttribute(SessionData.CUSTOMER_ID.toString());
        JsonResult jsonResult=new JsonResult();
        Subscribe subscribe = subscribeService.subscribe(wechatId, customerId);
        jsonResult.setObj(subscribe);
        return jsonResult;
    }


    /**
     * 取消订阅
     * */
    @ResponseBody
    @RequestMapping(value = {"/unsubscribe"})
    public JsonResult unsubscribe(@RequestParam String wechatId, HttpSession session){
        String customerId = (String)session.getAttribute(SessionData.CUSTOMER_ID.toString());
        JsonResult jsonResult=new JsonResult();
        subscribeService.unsubscribe(wechatId, customerId);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    /**
     * 订阅
     * */
    @ResponseBody
    @RequestMapping(value = {"/subscribeNewWechat"})
    public JsonResult subscribeNewWechat(@RequestParam String openId, HttpSession session){
        String customerId = (String)session.getAttribute(SessionData.CUSTOMER_ID.toString());
        JsonResult jsonResult=new JsonResult();
        Subscribe subscribe = subscribeService.subscribeByOpenId(openId, customerId);
        jsonResult.setObj(subscribe);
        return jsonResult;
    }

}
