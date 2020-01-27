package com.pawpaw.framework.core.ali;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liujixin
 * @date 2019-01-20
 * @description
 */

/**
 * @author liujixin
 * @date 2019-01-29
 * @description
 */
@ConfigurationProperties(prefix = "app.ali")
public class AliConfigProperties {

    private String accessKey;
    private String secrectKey;
    private String domainName;
    private String smsSignName;

    public String getSmsSignName() {
        return smsSignName;
    }

    public void setSmsSignName(String smsSignName) {
        this.smsSignName = smsSignName;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecrectKey() {
        return secrectKey;
    }

    public void setSecrectKey(String secrectKey) {
        this.secrectKey = secrectKey;
    }
}
