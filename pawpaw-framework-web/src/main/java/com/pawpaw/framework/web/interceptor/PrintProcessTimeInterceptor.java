package com.pawpaw.framework.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 打印接口处理时间消耗的拦截器
 *
 * @author liujixin
 */
public class PrintProcessTimeInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(PrintProcessTimeInterceptor.class);

    private static ThreadLocal<Long> timeSpend = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        timeSpend.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long start = timeSpend.get();
        if (start == null) {
            return;
        }
        long spend = System.currentTimeMillis() - start;
        logger.info("handler {} ,spend {} million seconds", handler, spend);

    }
}
