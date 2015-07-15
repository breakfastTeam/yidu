package com.smartbean.manage.controller;

import com.smartbean.common.PageConfig;
import com.smartbean.entity.Article;
import com.smartbean.entity.Wechat;
import com.smartbean.entity.WechatType;
import com.smartbean.service.WechatService;
import com.smartbean.util.fastjson.FastJson;
import com.smartbean.util.fastjson.JsonResult;
import com.smartbean.util.internet.Spider;
import com.steadystate.css.parser.SACParserCSS1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private WechatService wechatService;

    @ResponseBody
    @RequestMapping(value = {"/list"})
    public JsonResult getSubscribeArticles(@FastJson Wechat wechat, @FastJson Integer pageNo){
        JsonResult jsonResult = new JsonResult();
        if(wechat == null){
            wechat = new Wechat();
        }
        PageRequest pageRequest = new PageRequest(pageNo - 1, PageConfig.PAGE_SIZE, new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        Page<Wechat> page = wechatService.findAll(pageRequest, wechat);
        jsonResult.setObj(page);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = {"/changeWechatType"})
    public JsonResult changeWechatType(@RequestParam String wechatId, @RequestParam String wechatTypeId){
        JsonResult jsonResult = new JsonResult();
        wechatService.changeWechatType(wechatId, wechatTypeId);
        Wechat wechat = wechatService.findOne(wechatId);
        jsonResult.setObj(wechat);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = {"/searchWechatFromInternet"})
    public JsonResult searchWechatFromInternet(@RequestParam String name){
        JsonResult jsonResult = new JsonResult();
        List<Wechat> wechats = wechatService.searchWechatFromInternet(name);
        jsonResult.setObj(wechats);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = {"/save"})
    public JsonResult save(@FastJson Wechat wechat){
        JsonResult jsonResult = new JsonResult();
        wechat = wechatService.save(wechat);
        jsonResult.setObj(wechat);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = {"/delete"})
    public JsonResult save(@RequestParam String wechatId){
        JsonResult jsonResult = new JsonResult();
       wechatService.delete(wechatId);
        jsonResult.setSuccess(true);
        return jsonResult;
    }
}
