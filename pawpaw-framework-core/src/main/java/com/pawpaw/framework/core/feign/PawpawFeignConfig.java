package com.pawpaw.framework.core.feign;

import com.netflix.appinfo.EurekaInstanceConfig;
import com.pawpaw.framework.core.factory.json.ObjectMapperFactory;
import com.pawpaw.framework.core.factory.json.PawpawObjectMapper;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan("com.pawpaw.framework.core.feign")
public class PawpawFeignConfig {
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Primary
    @Bean
    public Decoder getDecoder() {
        PawpawObjectMapper objectMapper = ObjectMapperFactory.defaultObjectMapper();
        return new RemoteResultFeignDeocder(messageConverters, objectMapper, "utf-8");
    }


    @Bean
    public RequestInterceptor getHeaderInteceptor(EurekaInstanceConfig eurekaInstanceConfig) {
        String ip = eurekaInstanceConfig.getIpAddress();
        int port = eurekaInstanceConfig.getNonSecurePort();
        String hostName = eurekaInstanceConfig.getHostName(true);
        String appName = eurekaInstanceConfig.getAppname();
        FeignRequestHeader requestHeader = new FeignRequestHeader(appName, ip, String.valueOf(port), hostName);
        return new AddHeaderInterceptor(requestHeader.getHeaders());
    }


}