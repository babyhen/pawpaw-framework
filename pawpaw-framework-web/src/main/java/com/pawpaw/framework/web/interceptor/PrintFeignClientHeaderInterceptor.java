package com.pawpaw.framework.web.interceptor;

import com.pawpaw.framework.core.feign.FeignRequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 打印feign客户端的请求头，可以获取是哪个服务调用了这次请求
 *
 * @author liujixin
 * @date 2019-01-22
 */
public class PrintFeignClientHeaderInterceptor implements HandlerInterceptor {
    public static final Logger LOGGER = LoggerFactory.getLogger(PrintFeignClientHeaderInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String appName = request.getHeader(FeignRequestHeader.feignRequestHeaderNameApplicationName);
            String hostName = request.getHeader(FeignRequestHeader.feignRequestHeaderNameHostName);
            String hostIP = request.getHeader(FeignRequestHeader.feignRequestHeaderNameHostIP);
            String port = request.getHeader(FeignRequestHeader.feignRequestHeaderNameHostPort);

            if (LOGGER.isDebugEnabled()) {
                FeignRequestHeader header = new FeignRequestHeader(appName, hostIP, port, hostName);
                LOGGER.debug("feign client header : {}", header);
            }
        } catch (Exception e) {
            LOGGER.error("获取feign client header 失败，{}", e.getMessage());
        } finally {
            return true;
        }
    }
}
