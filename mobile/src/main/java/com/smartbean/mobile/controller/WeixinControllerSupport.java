package com.smartbean.mobile.controller;

import com.github.sd4324530.fastweixin.servlet.WeixinSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* 微信公众平台交互操作基类，提供几乎所有微信公众平台交互方式
* 基于springmvc框架，方便使用此框架的项目集成
*
* @author peiyu
*/
@Controller
public abstract class WeixinControllerSupport extends WeixinSupport {

    /**
     * 绑定微信服务器
     *
     * @param request 请求
     * @return 响应内容
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    protected final String bind(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if (isLegal(request)) {
            //绑定微信服务器成功
            return request.getParameter("echostr");
        } else {
            //绑定微信服务器失败
            return "";
        }
    }

    /**
     * 微信消息交互处理
     *
     * @param request http 请求对象
     * @return 响应给微信服务器的消息报文
     * @throws javax.servlet.ServletException 异常
     * @throws java.io.IOException      IO异常
     */
    @RequestMapping(method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    @ResponseBody
    protected final String process(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (!isLegal(request)) {
            return "";
        }
        String result = processRequest(request);
        return result;
    }
}
