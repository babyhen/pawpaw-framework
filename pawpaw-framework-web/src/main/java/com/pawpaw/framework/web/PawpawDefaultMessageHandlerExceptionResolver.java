package com.pawpaw.framework.web;


import com.pawpaw.framework.core.codec.RemoteResult;
import com.pawpaw.framework.core.codec.ResultEncoder;
import com.pawpaw.framework.core.factory.json.PawpawObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

class PawpawDefaultMessageHandlerExceptionResolver implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(PawpawDefaultMessageHandlerExceptionResolver.class);

    private final ResultEncoder resultEncoder;
    private final PawpawObjectMapper objectMapper;


    public PawpawDefaultMessageHandlerExceptionResolver(PawpawObjectMapper objectMapper, ResultEncoder resultEncoder) {
        this.resultEncoder = resultEncoder;
        this.objectMapper = objectMapper;
    }


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            StringBuffer url = request.getRequestURL();
            String params = request.getQueryString();
            LOGGER.error("url:{},params:{},ex:{}", url, params, ex.getMessage());
            RemoteResult<? extends Object> br = this.resultEncoder.encode(ex);
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


}

