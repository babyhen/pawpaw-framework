package com.pawpaw.framework.core.feign;

import lombok.ToString;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liujixin
 * @date 2019-01-20
 * @description
 */
@ToString
public class FeignRequestHeader {

    public static final String feignRequestHeaderNameApplicationName = "ReqAppName";
    public static final String feignRequestHeaderNameHostIP = "ReqHostIP";
    public static final String feignRequestHeaderNameHostName = "ReqHostName";
    public static final String feignRequestHeaderNameHostPort = "ReqHostPort";

    private Map<String, String> headers;

    public FeignRequestHeader(String appName, String ip, String port, String hostName) {
        appName = appName == null ? "" : appName;
        ip = ip == null ? "" : ip;
        port = port == null ? "" : port;
        hostName = hostName == null ? "" : hostName;
        //
        Map<String, String> temp = new HashMap<>();
        temp.put(feignRequestHeaderNameApplicationName, appName);
        temp.put(feignRequestHeaderNameHostIP, ip);
        temp.put(feignRequestHeaderNameHostPort, port);
        temp.put(feignRequestHeaderNameHostName, hostName);
        this.headers = Collections.unmodifiableMap(temp);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

}
