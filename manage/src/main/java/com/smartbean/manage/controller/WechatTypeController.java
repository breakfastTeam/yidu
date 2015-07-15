package com.smartbean.manage.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.smartbean.common.PageConfig;
import com.smartbean.entity.WechatType;
import com.smartbean.service.WechatTypeService;
import com.smartbean.util.fastjson.FastJson;
import com.smartbean.util.fastjson.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
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
@RequestMapping("/wechatType")
public class WechatTypeController {
    @Autowired
    private WechatTypeService wechatTypeService;

    /**
     * 获取所有类型信息并分页展示
     * **/
    @ResponseBody
    @RequestMapping(value = {"/list"})
    public JsonResult getSubscribeArticles(@FastJson WechatType wechatType, @FastJson Integer pageNo) {
        JsonResult jsonResult = new JsonResult();
        if (wechatType == null) {
            wechatType = new WechatType();
        }
        PageRequest pageRequest = new PageRequest(pageNo - 1, PageConfig.PAGE_SIZE, new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        Page<WechatType> pages = wechatTypeService.findAll(pageRequest, wechatType);
        jsonResult.setObj(pages);
        return jsonResult;
    }


    /**
     * 获取所有微信类型
     * **/
    @ResponseBody
    @RequestMapping(value = {"/getAllWechatType"})
    public JsonResult getAllWechatType() {
        JsonResult jsonResult = new JsonResult();
        List<WechatType> list = wechatTypeService.findAll();
        jsonResult.setObj(list);
        return jsonResult;
    }

    /**
     * 保存或更新类型
     * **/
    @ResponseBody
    @RequestMapping(value = {"/saveOrUpdate"})
    public JsonResult getSubscribeArticles(@RequestParam String wechatType, @RequestParam(required = false) String wechatTypes) {
        JsonResult jsonResult = new JsonResult();
        JSONObject wechatTypeObj = JSONObject.parseObject(wechatType);

        String name = (String)wechatTypeObj.get("name");
        String id = (String)wechatTypeObj.get("id");
        WechatType w;
        if(StringUtils.isNotBlank(id)){
            w = wechatTypeService.findOne(id);
        }else{
            w = new WechatType();
            w.setCreateTime(DateTime.now());
        }
        if(StringUtils.isNotBlank(name)){
            w.setName(name);
        }
        wechatTypeService.saveOrUpdate(w);

        if(StringUtils.isNotBlank(wechatTypes)) {
            JSONArray wechatTypesObj = JSONObject.parseArray("[" + wechatTypes + "]");
            List<WechatType> list = Lists.newArrayList();
            for (int i = 0; i < wechatTypesObj.size(); i++) {
                String secondName = (String) wechatTypesObj.getJSONObject(i).get("name");
                String secondId = (String) wechatTypesObj.getJSONObject(i).get("id");
                WechatType secondWechatType;
                if (StringUtils.isNotBlank(secondId)) {
                    secondWechatType = wechatTypeService.findOne(secondId);
                } else {
                    secondWechatType = new WechatType();
                    secondWechatType.setCreateTime(DateTime.now());
                    secondWechatType.setParentId(w.getId());
                }
                if (StringUtils.isNotBlank(secondName)) {
                    secondWechatType.setName(secondName);
                }
                list.add(secondWechatType);
            }
            wechatTypeService.save(list);
        }
        jsonResult.setObj(wechatType);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    /**
     * 获取类型详情
     * **/
    @ResponseBody
    @RequestMapping(value = {"/getWechatTypeDetail"})
    public JsonResult getWechatTypeDetail(@RequestParam String id) {
        JsonResult jsonResult = new JsonResult();
        WechatType subject = wechatTypeService.findOne(id);
        jsonResult.setObj(subject);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    /**
     * 获取所有一级分类
     * **/
    @ResponseBody
    @RequestMapping(value={"getAllFirstLevel"})
    public JsonResult getAllFirstLevel(){
        JsonResult jsonResult = new JsonResult();
        List<WechatType> wechatTypes = wechatTypeService.findAllFirstLevel();
        jsonResult.setObj(wechatTypes);
        return  jsonResult;
    }

    /**
     * 获取所有二级分类
     * **/
    @ResponseBody
    @RequestMapping(value={"getSecondLevel"})
    public JsonResult getSecondLevel(@RequestParam String parentId){
        JsonResult jsonResult = new JsonResult();
        List<WechatType> wechatTypes = wechatTypeService.findSecondLevel(parentId);
        jsonResult.setObj(wechatTypes);
        return  jsonResult;
    }


}
