package com.frk.blog.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)//拦截所有Exception
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        //记录异常
        logger.error("Request URL :{},Exception : {}",request.getRequestURL(),e);
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){//判断 if中方法（查找异常类，和异常码的类）返回布尔值 存在就不为空，不存在就为空

            throw e;
        }

        ModelAndView mv = new ModelAndView();
        //将获取到的url添加到对象中
        mv.addObject("URL",request.getRequestURL());
        //获取到的异常
        mv.addObject("Exception",e);
        //设置返回到的页面--viewname
        mv.setViewName("error");
//将对象返回
        return mv;
    }
}
