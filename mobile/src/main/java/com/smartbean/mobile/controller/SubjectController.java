package com.smartbean.mobile.controller;

import com.google.common.collect.Lists;
import com.smartbean.entity.Subject;
import com.smartbean.entity.Wechat;
import com.smartbean.model.SubjectModel;
import com.smartbean.service.SubjectService;
import com.smartbean.util.fastjson.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2015/6/25.
 */
@Controller
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 获取今日热门话题
     * */
    @ResponseBody
    @RequestMapping(value = {"/list"})
    public JsonResult list(){
        JsonResult jsonResult=new JsonResult();
        List<Subject> subjects = subjectService.findAll();
        List<SubjectModel> subjectModels = Lists.newArrayList();
        for(Subject subject : subjects){
            SubjectModel subjectModel = new SubjectModel();
            BeanUtils.copyProperties(subject, subjectModel);
            subjectModels.add(subjectModel);
        }
        jsonResult.setObj(subjectModels);
        return jsonResult;
    }
}
