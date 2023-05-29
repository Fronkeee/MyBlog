package com.frk.blog.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Request;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.hibernate.annotations.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

//需要记录日志的内容
//        请求的url
//        访问者ip
//        调用方法 classMethod
//        参数 args
//        返回内容
@Aspect
@Component
//aspect注解是创造切面  component springboot通过该注解可以找到该对象可以扫描到
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());//拿到日志记录器
    @Pointcut("execution(* com.frk.blog.web.*.*(..))")//通过该注解声明log方法是个切面，拦截该包下所有类和所有方法
    public void log(){


    }
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){//在切面之前执行,用来获取对应参数值
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();//创建获取对应值
        HttpServletRequest request = attributes.getRequest();//用request接收该容器attributes
        String url = request.getRequestURI().toString();//获取url
        String ip = request.getRemoteAddr();//获取ip地址
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();//获取切面中的类和方法名
        Object[] args =joinPoint.getArgs();//通过切面的方式获取请求参数的数组
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);//创建新对象接收各个参数


        logger.info("Request : {}",requestLog);

    }
    @After("log()")
    public void doAfter(){//在切面之后
//        logger.info("--------doAfter-------");

    }
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRetern(Object result){//在切面after方法执行后

        logger.info("--------Result : {}-------",result);
    }

    private class RequestLog{

        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
