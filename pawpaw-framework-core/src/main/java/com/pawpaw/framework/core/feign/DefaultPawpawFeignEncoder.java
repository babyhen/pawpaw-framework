package com.pawpaw.framework.core.feign;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * feign的编码器，主要是为了在请求的header中，加入请求客户端的的application name
 * 方便服务提供者获得服务的调用方信息
 *
 * @author liujixin
 * @date 2019-01-19
 */
public class DefaultPawpawFeignEncoder extends SpringEncoder {
    private Map<String, String> httpHeaders;

    public DefaultPawpawFeignEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        this(messageConverters, null);
    }

    public DefaultPawpawFeignEncoder(ObjectFactory<HttpMessageConverters> messageConverters, Map<String, String> httpHeaders) {
        super(messageConverters);
        this.httpHeaders = httpHeaders;
    }

    @Override
    public void encode(Object requestBody, Type bodyType, RequestTemplate request) throws EncodeException {
        if (this.httpHeaders != null) {
            for (String key : this.httpHeaders.keySet()) {
                request.header(key, this.httpHeaders.get(key));
            }
        }
        super.encode(requestBody, bodyType, request);
    }
}
