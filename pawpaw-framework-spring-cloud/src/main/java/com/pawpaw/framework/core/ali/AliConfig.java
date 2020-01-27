package com.pawpaw.framework.core.ali;

import com.aliyuncs.IAcsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(IAcsClient.class)
@EnableConfigurationProperties(AliConfigProperties.class)
public class AliConfig {


}