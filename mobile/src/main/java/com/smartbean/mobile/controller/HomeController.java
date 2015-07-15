package com.smartbean.mobile.controller;

import com.smartbean.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by Administrator on 2015/6/25.
 */
@Controller
@RequestMapping("/")
public class HomeController {




    /**
     * 获取今日热门话题
     * */
    @RequestMapping(value = {"/"})
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }
}
