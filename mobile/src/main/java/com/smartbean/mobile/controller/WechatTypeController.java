package com.smartbean.mobile.controller;

import com.google.common.collect.Lists;
import com.smartbean.entity.WechatType;
import com.smartbean.mobile.model.WechatTypeModel;
import com.smartbean.service.WechatService;
import com.smartbean.service.WechatTypeService;
import com.smartbean.util.fastjson.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2015/6/28.
 */
@Controller
@RequestMapping("/wechatType")
public class WechatTypeController {
    @Autowired
    private WechatTypeService wechatTypeService;


    /**
     * 获取系统中所有微信分类
     * */
    @ResponseBody
    @RequestMapping(value = {"/getWechatType"})
    public JsonResult getWechatType(){
        JsonResult jsonResult=new JsonResult();
        List<WechatType> firstLevel = wechatTypeService.findAllFirstLevel();
        List<WechatTypeModel> wechatTypeModels = Lists.newArrayList();
        for(WechatType wechatType : firstLevel){
            WechatTypeModel wechatTypeModel = new WechatTypeModel();
            wechatTypeModel.setFirstLevel(wechatType);
            List<WechatType> secondLevel = wechatTypeService.findSecondLevel(wechatType.getId());
            wechatTypeModel.setSecondLevel(secondLevel);
            wechatTypeModels.add(wechatTypeModel);
        }
        jsonResult.setObj(wechatTypeModels);
        return jsonResult;
    }

}
