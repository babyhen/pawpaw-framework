package com.pawpaw.framework.core.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 记录线程和对应的controller的关系拦截器
 *
 * @author liujixin
 */
public class ThreadHandlerMapInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ThreadHandlerMapInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {
            ThreadHandlerMappingHolder.put(handler);
        } catch (Exception e) {
            logger.info("{}", e);
        } finally {
            return true;
        }


    }


    public static class ThreadHandlerMappingHolder {
        private static ThreadLocal<Object> holder;

        public static void put(Object handler) {
            if (holder == null) {
                synchronized (ThreadHandlerMappingHolder.class) {
                    if (holder == null) {
                        holder = new ThreadLocal<>();

                    }
                }
            }
            holder.set(handler);
        }

        public static Object get() {
            if (holder == null) {
                return null;
            }
            return holder.get();
        }
    }
}
