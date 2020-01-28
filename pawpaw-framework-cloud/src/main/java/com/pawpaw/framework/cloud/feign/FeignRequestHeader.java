package com.pawpaw.framework.cloud.feign;

import lombok.ToString;

import java.util.*;

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

    private Map<String, Collection<String>> headers;

    public FeignRequestHeader(String appName, String ip, String port, String hostName) {
        appName = appName == null ? "" : appName;
        Collection<String> appNames = new HashSet<>();
        appNames.add(appName);
        //
        ip = ip == null ? "" : ip;
        Collection<String> ips = new HashSet<>();
        ips.add(ip);
        //
        port = port == null ? "" : port;
        Collection<String> ports = new HashSet<>();
        ports.add(port);

        //
        hostName = hostName == null ? "" : hostName;
        Collection<String> hostNames = new HashSet<>();
        hostNames.add(hostName);
        //
        Map<String, Collection<String>> temp = new HashMap<>();
        temp.put(feignRequestHeaderNameApplicationName, appNames);
        temp.put(feignRequestHeaderNameHostIP, ips);
        temp.put(feignRequestHeaderNameHostPort, ports);
        temp.put(feignRequestHeaderNameHostName, hostNames);
        this.headers = Collections.unmodifiableMap(temp);
    }

    public Map<String, Collection<String>> getHeaders() {
        return this.headers;
    }

}
