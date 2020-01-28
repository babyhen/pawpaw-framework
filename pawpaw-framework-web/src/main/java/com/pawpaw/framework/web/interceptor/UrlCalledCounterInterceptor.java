package com.pawpaw.framework.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 统计url访问次数的拦截器，重启服务后，记录的数据就消失了。
 * 目前是为了看下哪些接口没有被调用。后续可以删掉接口对应的代码
 *
 * @author liujixin
 * @date 2019-01-22
 */
public class UrlCalledCounterInterceptor implements HandlerInterceptor {
    public static final Logger LOGGER = LoggerFactory.getLogger(UrlCalledCounterInterceptor.class);


    @Override

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //避免影响正常的业务
        try {
            final String url = request.getServletPath();
            CountNumHolder.increase(url);
        } catch (Throwable e) {
            LOGGER.error("{}", e.getMessage());
        } finally {
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //noop
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //noop
    }

    /////////////////////////////////////////////////
    /////////////////////////////////////////////////
    public static class CountNumHolder {
        //单例
        private static Map<String, AtomicLong> counterMap;

        /**
         * 计数器+1
         *
         * @Auther: liujixin
         * @Date: 2019-01-22
         */
        public static void increase(String url) {
            if (url == null) {
                return;
            }
            //初始化map
            if (counterMap == null) {
                synchronized (CountNumHolder.class) {
                    if (counterMap == null) {
                        counterMap = new HashMap<>();
                    }
                }
            }

            //初始化 计数器

            AtomicLong c = counterMap.get(url);
            if (c == null) {
                synchronized (counterMap) {
                    c = counterMap.get(url);
                    if (c == null) {
                        c = new AtomicLong(0);
                        counterMap.put(url, c);
                    }
                }
            }
            c.incrementAndGet();

        }


        public static Map<String, Long> getResult() {
            if (counterMap == null) {
                return new HashMap<>();
            }
            //拷贝个副本，防止遍历的时候， counterMap改变影响遍历
            Map<String, AtomicLong> copy = new HashMap<>();
            copy.putAll(counterMap);


            Map<String, Long> r = new HashMap<>();
            for (String key : copy.keySet()) {
                r.put(key, copy.get(key).longValue());
            }
            return r;
        }
    }
}
