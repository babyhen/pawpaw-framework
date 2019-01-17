package com.pawpaw.framework.core.web;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 打印请求参数的拦截器
 *
 * @author liujixin
 */
public class PrintRequestDataInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(PrintRequestDataInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {
            if (handler != null) {
                String path = request.getServletPath();
                Map<String, String[]> parameterMap = request.getParameterMap();
                StringBuilder sb = new StringBuilder();
                for (Entry<String, String[]> entry : parameterMap.entrySet()) {
                    sb.append("[");
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append(StringUtils.join(entry.getValue(), ","));
                    sb.append("]");
                }
                logger.info("{}请求的参数：{}", path, sb.toString());
            }
        } catch (Throwable e) {
            logger.error("获取请求参数失败,{}", e.getMessage());
        }

        return true;
    }

}
