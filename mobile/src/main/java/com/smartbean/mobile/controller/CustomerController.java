package com.smartbean.mobile.controller;

import com.google.common.collect.Lists;
import com.smartbean.bean.SBPage;
import com.smartbean.common.PageConfig;
import com.smartbean.common.SessionData;
import com.smartbean.common.TimeType;
import com.smartbean.entity.Article;
import com.smartbean.entity.Customer;
import com.smartbean.entity.ReadLog;
import com.smartbean.mobile.model.CustomerModel;
import com.smartbean.model.BriefArticleModel;
import com.smartbean.model.DetailArticleModel;
import com.smartbean.model.WechatArticleModel;
import com.smartbean.repository.CustomerRepository;
import com.smartbean.service.ArticleService;
import com.smartbean.service.CustomerService;
import com.smartbean.service.ReadLogService;
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
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    /**
     * 获取微信号下的文章信息
     * */
    @ResponseBody
    @RequestMapping(value = {"/getCustomerInfo"})
    public JsonResult getWechatArticles(HttpSession session){
        String customerId = session.getAttribute(SessionData.CUSTOMER_ID.toString()).toString();
        Customer customer = customerService.findOne(customerId);
        JsonResult jsonResult=new JsonResult();
        CustomerModel customerModel = new CustomerModel();
        customerModel.setId(customer.getId());
        customerModel.setAvatar(customer.getAvatar());
        customerModel.setName(customer.getNickName());
        jsonResult.setObj(customerModel);
        return jsonResult;
    }
}
