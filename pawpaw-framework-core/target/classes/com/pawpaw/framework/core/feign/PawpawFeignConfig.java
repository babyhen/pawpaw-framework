package com.pawpaw.framework.core.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawpaw.framework.core.config.PawpawFrameworkConfigProperty;
import com.pawpaw.framework.core.factory.json.ObjectMapperFactory;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

@Configuration
public class PawpawFeignConfig {
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;
    @Autowired
    private PawpawFrameworkConfigProperty configProperty;
    @Autowired(required = false)
    private ServerProperties serverProperties;
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public FeignRequestHeader getFeignRequestHeader() {
        String id = this.applicationContext.getId();
        String hostName = "";
        String ip = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            //获取本机ip
            ip = addr.getHostAddress().toString();
            //获取本机计算机名称
            hostName = addr.getHostName().toString();
        } catch (Exception e) {
            //noop
        }
        String port = "";
        if (this.serverProperties != null) {
            Integer p = this.serverProperties.getPort();
            port = p == null ? "" : p.toString();
        }

        return new FeignRequestHeader(id, ip, port, hostName);
    }

    @Bean
    public Decoder getDecoder() {
        ObjectMapper objectMapper = ObjectMapperFactory.defaultObjectMapper();
        return new RemoteResultFeignDeocder(messageConverters, objectMapper, "utf-8");
    }

    @Bean
    @Autowired
    public Encoder getEncoder(FeignRequestHeader requestHeader) {
        return new DefaultPawpawFeignEncoder(this.messageConverters, requestHeader.getHeaders());
    }


}