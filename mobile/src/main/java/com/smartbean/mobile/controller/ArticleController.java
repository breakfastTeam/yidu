package com.smartbean.mobile.controller;

import com.google.common.collect.Lists;
import com.smartbean.bean.SBPage;
import com.smartbean.common.PageConfig;
import com.smartbean.common.SessionData;
import com.smartbean.common.TimeType;
import com.smartbean.entity.Article;
import com.smartbean.entity.ReadLog;
import com.smartbean.entity.Subject;
import com.smartbean.entity.Wechat;
import com.smartbean.model.BriefArticleModel;
import com.smartbean.model.DetailArticleModel;
import com.smartbean.model.WechatArticleModel;
import com.smartbean.service.ArticleService;
import com.smartbean.service.ReadLogService;
import com.smartbean.service.SubjectService;
import com.smartbean.service.WechatService;
import com.smartbean.util.fastjson.JsonResult;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private WechatService wechatService;


    /**
     * 获取某话题下的文章信息
     * */
    @ResponseBody
    @RequestMapping(value = {"/getSubjectArticles"})
    public JsonResult getSubjectArticles(@RequestParam String subjectId, @RequestParam int pageNo ){
        JsonResult jsonResult=new JsonResult();
        PageRequest pageRequest = new PageRequest(pageNo-1, PageConfig.PAGE_SIZE ,new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        Page<Article> page = articleService.getBySubjectId(pageRequest, subjectId);

        SBPage<BriefArticleModel> sbPage = new SBPage<BriefArticleModel>();
        sbPage.setTotalPage(page.getTotalPages());
        sbPage.setLast(page.isLast());
        List<BriefArticleModel> briefArticleModels = Lists.newArrayList();

        for(Article article : page.getContent()){
            BriefArticleModel briefArticleModel = new BriefArticleModel();
            briefArticleModel.setAuthor(article.getAuthor());
            briefArticleModel.setBriefIntro(article.getBriefIntro());
            briefArticleModel.setDetailUrl(article.getDetailUrl());
            briefArticleModel.setId(article.getId());
            briefArticleModel.setLogo(article.getLogo());
            briefArticleModel.setReadTimes(article.getReadTimes());
            briefArticleModel.setTitle(article.getTitle());
            briefArticleModel.setSubjectName(article.getSubject() != null ? article.getSubject().getName() : null);
            briefArticleModel.setWechatName(article.getWechat() != null ? article.getWechat().getName() : null);
            long time = article.getCreateTime().getMillis();
            long now = DateTime.now().getMillis();
            long minus = (now-time)/(1000*60);
            long hour = (now-time)/(1000*60*60);
            long day = (now-time)/(1000*60*60*24);
            if(minus<60){
                briefArticleModel.setTimeType(TimeType.MINUTE.toString());
                briefArticleModel.setCreateTime(minus+"");
            }else if(hour<24){
                briefArticleModel.setTimeType(TimeType.HOUR.toString());
                briefArticleModel.setCreateTime(hour+"");
            }else if(day>=1&&day<=99){
                briefArticleModel.setTimeType(TimeType.DAY.toString());
                briefArticleModel.setCreateTime(day+"");
            }else{
                briefArticleModel.setTimeType(TimeType.OTHER.toString());
                briefArticleModel.setCreateTime(day+"");
            }

            briefArticleModels.add(briefArticleModel);

        }
        sbPage.setContent(briefArticleModels);
        jsonResult.setObj(sbPage);
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
        PageRequest pageRequest = new PageRequest(pageNo-1, PageConfig.SMALL_PAGE_SIZE ,new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
//        Page<Article> page = articleService.getByCustomer(pageRequest, customerId);
        List<WechatArticleModel> wechatArticleModels = articleService.getSubscribeArticle(pageRequest, customerId);
        jsonResult.setObj(wechatArticleModels);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = {"/getSimilarLatestArticle"})
    public JsonResult getSimilarLatestArticle(@RequestParam String articleId){
        Article article = articleService.findOne(articleId);
        String wechatId = article.getWechatId();
        String subjectId = article.getSubjectId();
        JsonResult jsonResult = new JsonResult();
        List<BriefArticleModel> briefArticleModels = articleService.getSimilarLatestArticle(articleId, wechatId, subjectId);
        jsonResult.setObj(briefArticleModels);
        return jsonResult;
    }

    /**
     * 获取我关注的微信号的文章
     * */
    @ResponseBody
    @RequestMapping(value = {"/getArticleDetail"})
    public JsonResult getArticleDetail(@RequestParam String articleId, @RequestParam String shareCustomerId, HttpSession session){
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
            readLog.setShareCustomerId(shareCustomerId);
            readLog.setCreateTime(DateTime.now());
            readLogService.save(readLog);
        }


        DetailArticleModel articleModel = new DetailArticleModel();
        articleModel.setReadTimes(article.getReadTimes());
        articleModel.setWechatName(article.getWechat() != null ? article.getWechat().getName() : "From Internet");
        articleModel.setContent(article.getContent());
        articleModel.setId(article.getId());
        articleModel.setSubjectName(article.getSubject() != null ? article.getSubject().getName() : "unknown");
        articleModel.setAuthor(article.getAuthor());
        articleModel.setTitle(article.getTitle());
        articleModel.setWechatLogo(article.getWechat() != null ? article.getWechat().getLogo() : "");
        articleModel.setCreateTime(article.getCreateTime().toString("yyyy-MM-dd HH:mm:ss"));

        jsonResult.setObj(articleModel);
        return jsonResult;
    }

    @RequestMapping(value = {"/articleDetail"})
    public ModelAndView articleDetail(@RequestParam String articleId, HttpSession session){
        ModelAndView mv = new ModelAndView();
        Article article = articleService.findOne(articleId);
        article.setReadTimes(article.getReadTimes()+1);
        articleService.saveOrUpdate(article);

        String customerId = (String)session.getAttribute(SessionData.CUSTOMER_ID.toString());
        boolean isRead = readLogService.isRead(customerId, article.getId());
        if(!isRead){
            ReadLog readLog = new ReadLog();
            readLog.setCustomerId(customerId);
            readLog.setArticleId(article.getId());
            readLog.setCreateTime(DateTime.now());
            readLogService.save(readLog);
        }

        DetailArticleModel articleModel = new DetailArticleModel();
        articleModel.setReadTimes(article.getReadTimes());
        articleModel.setWechatName(article.getWechat() != null ? article.getWechat().getName() : "From Internet");
        articleModel.setContent(article.getContent());
        articleModel.setId(article.getId());
        articleModel.setSubjectName(article.getSubject() != null ? article.getSubject().getName() : "unknown");
        articleModel.setAuthor(article.getAuthor());
        articleModel.setTitle(article.getTitle());
        articleModel.setWechatLogo(article.getWechat() != null ? article.getWechat().getLogo() : "");
        articleModel.setCreateTime(article.getCreateTime().toString("yyyy-MM-dd HH:mm:ss"));
        mv.addObject("article", articleModel);
        mv.setViewName("views/articleDetail");
        return mv;
    }

}
