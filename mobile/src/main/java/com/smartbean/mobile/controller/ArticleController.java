package com.smartbean.mobile.controller;

import com.smartbean.common.PageConfig;
import com.smartbean.common.SessionData;
import com.smartbean.entity.Article;
import com.smartbean.entity.ReadLog;
import com.smartbean.entity.Subject;
import com.smartbean.service.ArticleService;
import com.smartbean.service.ReadLogService;
import com.smartbean.service.SubjectService;
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
 * Created by Administrator on 2015/6/25.
 */

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ReadLogService readLogService;

    /**
     * 获取某话题下的文章信息
     * */
    @ResponseBody
    @RequestMapping(value = {"/getSubjectArticles"})
    public JsonResult getSubjectArticles(@RequestParam String subjectId, @RequestParam int pageNo ){
        JsonResult jsonResult=new JsonResult();
        PageRequest pageRequest = new PageRequest(pageNo-1, PageConfig.PAGE_SIZE ,new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        Page<Article> articles = articleService.getBySubjectId(pageRequest, subjectId);
        jsonResult.setObj(articles);
        return jsonResult;
    }

    /**
     * 获取微信号下的文章信息
     * */
    @ResponseBody
    @RequestMapping(value = {"/getWechatArticles"})
    public JsonResult getWechatArticles(@RequestParam String wechatId , @RequestParam int pageNo){
        JsonResult jsonResult=new JsonResult();
        PageRequest pageRequest = new PageRequest(pageNo-1, PageConfig.PAGE_SIZE ,new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        Page<Article> articles = articleService.getByWechat(pageRequest, wechatId);
        jsonResult.setObj(articles);
        return jsonResult;
    }

    /**
     * 获取我关注的微信号的文章
     * */
    @ResponseBody
    @RequestMapping(value = {"/getSubscribeArticles"})
    public JsonResult getSubscribeArticles(@RequestParam int pageNo, HttpSession session){
        JsonResult jsonResult=new JsonResult();
        String customerId = (String)session.getAttribute(SessionData.CUSTOMER_ID.toString());
        PageRequest pageRequest = new PageRequest(pageNo-1, PageConfig.PAGE_SIZE ,new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        Page<Article> page = articleService.getByCustomer(pageRequest, customerId);

        jsonResult.setObj(page);
        return jsonResult;
    }

    /**
     * 获取我关注的微信号的文章
     * */
    @ResponseBody
    @RequestMapping(value = {"/getArticleDetail"})
    public JsonResult getArticleDetail(@RequestParam String articleId, HttpSession session){
        String customerId = (String)session.getAttribute(SessionData.CUSTOMER_ID.toString());

        JsonResult jsonResult=new JsonResult();
        Article article = articleService.findOne(articleId);
        article.setReadTimes(article.getReadTimes()+1);
        articleService.saveOrUpdate(article);
        boolean isRead = readLogService.isRead(customerId, article.getId());
        if(!isRead){
            ReadLog readLog = new ReadLog();
            readLog.setCustomerId(customerId);
            readLog.setArticleId(article.getId());
            readLog.setCreateTime(DateTime.now());
            readLogService.save(readLog);
        }
        jsonResult.setObj(article);
        return jsonResult;
    }

}
