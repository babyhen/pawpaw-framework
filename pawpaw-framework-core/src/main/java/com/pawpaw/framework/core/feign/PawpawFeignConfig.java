package com.pawpaw.framework.core.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.pawpaw.framework.core.factory.json.ObjectMapperFactory;
import com.pawpaw.framework.core.factory.json.PawpawObjectMapper;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

public class PawpawFeignConfig {
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Autowired
    @Bean
    public FeignRequestHeader getFeignRequestHeader(EurekaInstanceConfig eurekaInstanceConfig) {
        String ip = eurekaInstanceConfig.getIpAddress();
        int port = eurekaInstanceConfig.getNonSecurePort();
        String hostName = eurekaInstanceConfig.getHostName(true);
        String appName = eurekaInstanceConfig.getAppname();
        return new FeignRequestHeader(appName, ip, String.valueOf(port), hostName);
    }

    @Bean
    public Decoder getDecoder() {
        PawpawObjectMapper objectMapper = ObjectMapperFactory.defaultObjectMapper();
        return new RemoteResultFeignDeocder(messageConverters, objectMapper, "utf-8");
    }

    @Bean
    @Autowired
    public Encoder getEncoder(FeignRequestHeader requestHeader) {
        return new DefaultPawpawFeignEncoder(this.messageConverters, requestHeader.getHeaders());
    }


}