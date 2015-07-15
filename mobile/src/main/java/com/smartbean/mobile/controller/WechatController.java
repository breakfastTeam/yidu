package com.smartbean.mobile.controller;

import com.google.common.collect.Lists;
import com.smartbean.common.PageConfig;
import com.smartbean.common.SessionData;
import com.smartbean.entity.Subscribe;
import com.smartbean.entity.Wechat;
import com.smartbean.mobile.model.WechatModel;
import com.smartbean.mobile.model.WechatPageModel;
import com.smartbean.service.SubscribeService;
import com.smartbean.service.WechatService;
import com.smartbean.util.fastjson.JsonResult;
import com.smartbean.util.internet.Spider;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2015/6/25.
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    private WechatService wechatService;

    @Autowired
    private SubscribeService subscribeService;


    /**
     * 获取微信公众账号详细信息
     */
    @ResponseBody
    @RequestMapping(value = {"/wechatDetail"})
    public JsonResult index(@RequestParam String id, HttpSession session) {
        String customerId = (String)session.getAttribute(SessionData.CUSTOMER_ID.toString());
        JsonResult jsonResult = new JsonResult();
        Wechat wechat = wechatService.findOne(id);
        WechatModel wechatModel = this.generateWechatModel(wechat, customerId);
        jsonResult.setObj(wechatModel);
        return jsonResult;
    }

    /**
     * 搜索本地微信公众账号
     */
    @ResponseBody
    @RequestMapping(value = {"/searchWechat"})
    public JsonResult searchWechat(@RequestParam String name, @RequestParam int pageNo, HttpSession session) {
        String customerId = (String) session.getAttribute(SessionData.CUSTOMER_ID.toString());
        PageRequest pageRequest = new PageRequest(pageNo-1, PageConfig.PAGE_SIZE ,new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        JsonResult jsonResult = new JsonResult();
        Wechat wechat = new Wechat();
        wechat.setName(name);
        Page<Wechat> page = wechatService.findAll(pageRequest, wechat);
        List<WechatModel> wechatModels = this.generateWechatModel(page.getContent(), customerId);
        WechatPageModel wechatPageModel = new WechatPageModel();
        wechatPageModel.setWechatModel(wechatModels);
        wechatPageModel.setLast(page.isLast());
        jsonResult.setObj(wechatPageModel);
        return jsonResult;
    }

    /**
     * 搜索互联网微信公众账号
     */
    @ResponseBody
    @RequestMapping(value = {"/searchWechatFromInternet"})
    public JsonResult searchWechatFromInternet(@RequestParam String name, HttpSession session) {
        JsonResult jsonResult = new JsonResult();
        String customerId = (String)session.getAttribute(SessionData.CUSTOMER_ID.toString());
        List<Wechat> wechatList = wechatService.searchWechatFromInternet(name);
        List<WechatModel> wechatModels = this.generateWechatModel(wechatList, customerId);
        jsonResult.setObj(wechatModels);
        return jsonResult;
    }

    /**
     * 获取分类下的所有微信号
     */
    @ResponseBody
    @RequestMapping(value = {"/getWechatByType"})
    public JsonResult getWechatByType(@RequestParam String typeId, @RequestParam int pageNo, HttpSession session) {
        JsonResult jsonResult = new JsonResult();
        PageRequest pageRequest = new PageRequest(pageNo-1, PageConfig.PAGE_SIZE ,new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        String customerId = (String) session.getAttribute(SessionData.CUSTOMER_ID.toString());
        Page<Wechat> page = wechatService.findByType(pageRequest, typeId);
        List<WechatModel> wechatModels = this.generateWechatModel(page.getContent(), customerId);
        WechatPageModel wechatPageModel = new WechatPageModel();
        wechatPageModel.setLast(page.isLast());
        wechatPageModel.setWechatModel(wechatModels);
        jsonResult.setObj(wechatPageModel);
        return jsonResult;
    }


    /**
     * 获取分类下的所有微信号
     */
    @ResponseBody
    @RequestMapping(value = {"/getSubscribeWechat"})
    public JsonResult getSubscribeWechat(HttpSession session) {
        JsonResult jsonResult = new JsonResult();
        String customerId = (String) session.getAttribute(SessionData.CUSTOMER_ID.toString());
        List<Wechat> wechatList = wechatService.findByCustomerId(customerId);
        List<WechatModel> wechatModels = this.generateWechatModel(wechatList, customerId);
        jsonResult.setObj(wechatModels);
        return jsonResult;
    }


    private List<WechatModel> generateWechatModel(List<Wechat> wechatList, String customerId) {

        List<Subscribe> subscribeList = subscribeService.findByCustomerId(customerId);
        List<String> wechatIds = Lists.newArrayList();
        for (Subscribe sb : subscribeList) {
            wechatIds.add(sb.getWechatId());
        }

        List<WechatModel> wechatModels = Lists.newArrayList();
        for (Wechat wechat : wechatList) {
            WechatModel wechatModel = new WechatModel();
            wechatModel.setLogo(wechat.getLogo());
            wechatModel.setOpenId(wechat.getOpenId());
            wechatModel.setName(wechat.getName());
            wechatModel.setAccount(wechat.getAccount());
            wechatModel.setIntro(wechat.getBriefIntro());
            wechatModel.setSubCount(wechat.getNum());
            wechatModel.setSub(wechatIds.contains(wechat.getId()));
            wechatModel.setId(wechat.getId());
            wechatModel.setCompany(wechat.getCompany());
            wechatModels.add(wechatModel);
        }
        return wechatModels;
    }
    private WechatModel generateWechatModel(Wechat wechat, String customerId) {
        WechatModel wechatModel;
        List<Wechat> wechatList = Lists.newArrayList(wechat);
        return this.generateWechatModel(wechatList, customerId).get(0);
    }
}
