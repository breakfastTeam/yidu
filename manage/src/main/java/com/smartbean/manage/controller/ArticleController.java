package com.smartbean.manage.controller;

import com.smartbean.common.PageConfig;
import com.smartbean.entity.Article;
import com.smartbean.service.ArticleService;
import com.smartbean.util.fastjson.FastJson;
import com.smartbean.util.fastjson.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2015/6/24.
 */
@Controller("articleController")
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ResponseBody
    @RequestMapping(value = {"/list"})
    public JsonResult getSubscribeArticles(@FastJson Article article, @FastJson Integer pageNo){
        JsonResult jsonResult = new JsonResult();
        PageRequest pageRequest = new PageRequest(pageNo-1, PageConfig.PAGE_SIZE ,new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        if(article == null){
            article = new Article();
        }
        Page page = articleService.findAll(pageRequest, article);
        jsonResult.setObj(page);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = {"/getArticleDetail"})
    public JsonResult getArticleDetail(@RequestParam String articleId){
        JsonResult jsonResult = new JsonResult();
        Article article = articleService.findOne(articleId);
        jsonResult.setObj(article);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = {"/publish"})
    public JsonResult publish(@RequestParam String articleId){
        JsonResult jsonResult = new JsonResult();
        Article article = articleService.publish(articleId);
        jsonResult.setObj(article);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = {"/unpublish"})
    public JsonResult unpublish(@RequestParam String articleId){
        JsonResult jsonResult = new JsonResult();
        Article article = articleService.unpublish(articleId);
        jsonResult.setObj(article);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = {"/changeSubject"})
    public JsonResult changeSubject(@RequestParam String articleId, @RequestParam String subjectId){
        JsonResult jsonResult = new JsonResult();
        articleService.changeSubject(articleId, subjectId);
        Article article = articleService.findOne(articleId);
        jsonResult.setObj(article);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

}
