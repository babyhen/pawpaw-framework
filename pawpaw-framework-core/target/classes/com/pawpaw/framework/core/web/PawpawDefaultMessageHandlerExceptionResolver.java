package com.pawpaw.framework.core.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawpaw.framework.core.exception.PawpawFrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

class PawpawDefaultMessageHandlerExceptionResolver implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(PawpawDefaultMessageHandlerExceptionResolver.class);
    private Charset charset;
    private ObjectMapper objectMapper;

    public PawpawDefaultMessageHandlerExceptionResolver(ObjectMapper objectMapper) {
        this(objectMapper, "UTF-8");
    }

    public PawpawDefaultMessageHandlerExceptionResolver(ObjectMapper objectMapper, String charset) {
        this.charset = Charset.forName(charset);
        this.objectMapper = objectMapper;
    }


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            StringBuffer url = request.getRequestURL();
            String params = request.getQueryString();
            RemoteResult br = null;
            if (ex == null) {
                //通常这类异常基本没有
                LOGGER.error("Exception->url: {}, params: {},error:{}", url, params, ex);
                br = new RemoteResult(RemoteResultCode.FAIL, null);

            } else if (ex instanceof PawpawFrameworkException) {
                PawpawFrameworkException pe = (PawpawFrameworkException) ex;
                //这类异常是基本的业务上的异常，打印info级别即可个errormsg即可
                LOGGER.info("PawpawException->url: {}, params: {},errMsg: {}", url, params, pe.getMessage());
                br = new RemoteResult(RemoteResultCode.FAIL, pe.getMessage(), null);
            } else {
                //这类异常是开发时期没有预料到的异常，需要把线程栈打印出来
                LOGGER.error("Exception->url: {}, params: {},error: {}", url, params, ex);
                br = new RemoteResult(RemoteResultCode.FAIL, ex.getMessage(), null);
            }
            byte[] data = this.objectMapper.writeValueAsBytes(br);
            if (data != null) {
                OutputStream out = response.getOutputStream();
                out.write(data);
                out.flush();
            }
            //返回一个空的ModelAndView，来代表已经成功处理此异常
            return new ModelAndView();
        } catch (IOException e) {
            //代表该异常处理器没有成功处理这个异常
            return null;
        }

    }


    /////////////////getter and setter////////////

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

}

