package com.pawpaw.framework.core.feign;

import com.netflix.appinfo.EurekaInstanceConfig;
import com.pawpaw.framework.core.codec.ResultDeocder;
import com.pawpaw.framework.core.factory.json.ObjectMapperFactory;
import com.pawpaw.framework.core.factory.json.PawpawObjectMapper;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PawpawFeignConfig {
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Primary
    @Bean
    public Decoder getDecoder() {
        PawpawObjectMapper objectMapper = ObjectMapperFactory.defaultObjectMapper();
        ResultDeocder resultDeocder = new ResultDeocder(objectMapper);
        return new RemoteResultFeignDeocder(messageConverters, resultDeocder, "utf-8");
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