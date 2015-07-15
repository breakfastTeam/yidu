package com.smartbean.manage.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by kkk on .
 */

public class ExecutionInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger(ExecutionInterceptor.class);

    @Override
    /**
     * toFix: appId appSecret => md5
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest()
                .getSession();
        String uri = request.getRequestURI();
        String ctx = request.getContextPath();
        int start = ctx.length() + 1;
        String substring = StringUtils.substring(uri, start, start + 25);

        String[] params = substring.split("/");
        if(true){
            return true;
        } else {
            log.info("Interceptor：无效APPID，跳转到error页面！");
            request.getRequestDispatcher("/app/views/sys/error.html").forward(request, response);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
    }
}
