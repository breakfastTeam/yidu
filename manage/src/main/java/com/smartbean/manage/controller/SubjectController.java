package com.smartbean.manage.controller;

import com.smartbean.common.PageConfig;
import com.smartbean.entity.Subject;
import com.smartbean.service.SubjectService;
import com.smartbean.util.fastjson.FastJson;
import com.smartbean.util.fastjson.JsonResult;
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
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ResponseBody
    @RequestMapping(value = {"/list"})
    public JsonResult getSubscribeArticles(@FastJson Subject subject, @FastJson Integer pageNo){
        JsonResult jsonResult = new JsonResult();
        if(subject == null){
            subject = new Subject();
        }
        PageRequest pageRequest = new PageRequest(pageNo-1, PageConfig.PAGE_SIZE ,new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        Page<Subject> pages= subjectService.findAll(pageRequest, subject);
        jsonResult.setObj(pages);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = {"/getAllSubject"})
    public JsonResult getAllSubject(){
        JsonResult jsonResult = new JsonResult();
        List<Subject> list= subjectService.findAll();
        jsonResult.setObj(list);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = {"/saveOrUpdate"})
    public JsonResult getSubscribeArticles(@FastJson Subject subject){
        JsonResult jsonResult = new JsonResult();
        subject.setCreateTime(DateTime.now());
        subjectService.saveOrUpdate(subject);
        jsonResult.setObj(subject);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = {"/getSubjectDetail"})
    public JsonResult getSubjectDetail(@RequestParam String id){
        JsonResult jsonResult = new JsonResult();
        Subject subject = subjectService.findOne(id);
        jsonResult.setObj(subject);
        jsonResult.setSuccess(true);
        return jsonResult;
    }
}
