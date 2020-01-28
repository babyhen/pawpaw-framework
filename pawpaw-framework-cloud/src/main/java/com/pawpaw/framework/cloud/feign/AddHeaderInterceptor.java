package com.pawpaw.framework.cloud.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Collection;
import java.util.Map;

/**
 * @author liujixin
 * @date 2019-02-12
 */

public class AddHeaderInterceptor implements RequestInterceptor {
    private Map<String, Collection<String>> httpHeaders;


    public AddHeaderInterceptor(Map<String, Collection<String>> httpHeaders) {

        this.httpHeaders = httpHeaders;
    }

    @Override
    public void apply(RequestTemplate template) {
        if (this.httpHeaders != null) {
            template.headers(this.httpHeaders);
        }
    }
}