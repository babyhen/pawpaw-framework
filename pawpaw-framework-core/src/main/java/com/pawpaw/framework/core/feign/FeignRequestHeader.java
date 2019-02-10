package com.pawpaw.framework.core.feign;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liujixin
 * @date 2019-01-20
 * @description
 */
public class FeignRequestHeader {

    public final String feignRequestHeaderNameApplicationName = "ReqAppName";
    public final String feignRequestHeaderNameHostIP = "ReqHostIP";
    public final String feignRequestHeaderNameHostName = "ReqHostName";
    public final String feignRequestHeaderNameHostPort = "ReqHostPort";

    private Map<String, String> headers;

    public FeignRequestHeader(String appName, String ip, int port, String hostName) {
        appName = appName == null ? "" : appName;
        ip = ip == null ? "" : ip;
        hostName = hostName == null ? "" : hostName;
        //
        Map<String, String> temp = new HashMap<>();
        temp.put(feignRequestHeaderNameApplicationName, appName);
        temp.put(feignRequestHeaderNameHostIP, ip);
        temp.put(feignRequestHeaderNameHostPort, String.valueOf(port));
        temp.put(feignRequestHeaderNameHostName, hostName);
        this.headers = Collections.unmodifiableMap(temp);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

}
