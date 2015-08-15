package com.smartbean.mobile.controller;

import com.google.common.collect.Lists;
import com.smartbean.bean.SBPage;
import com.smartbean.common.PageConfig;
import com.smartbean.common.SessionData;
import com.smartbean.common.TimeType;
import com.smartbean.entity.Article;
import com.smartbean.entity.ReadLog;
import com.smartbean.entity.ShareLog;
import com.smartbean.model.BriefArticleModel;
import com.smartbean.model.DetailArticleModel;
import com.smartbean.model.WechatArticleModel;
import com.smartbean.service.ArticleService;
import com.smartbean.service.ReadLogService;
import com.smartbean.service.ShareLogService;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2015/6/25.
 */

@Controller
@RequestMapping("/shareLog")
public class ShareLogController {

    @Autowired
    private ShareLogService shareLogService;


    /**
     * 获取某话题下的文章信息
     * */
    @ResponseBody
    @RequestMapping(value = {"/save"})
    public void getSubjectArticles(@RequestParam String articleId,@RequestParam String shareType, HttpSession session){
        String shareCustomerId = session.getAttribute(SessionData.CUSTOMER_ID.toString()).toString();
        ShareLog shareLog = new ShareLog();
        shareLog.setCustomerId(shareCustomerId);
        shareLog.setArticleId(articleId);
        shareLog.setCreateTime(DateTime.now());
        shareLogService.save(shareLog);
    }

}
