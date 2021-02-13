package com.pawpaw.framework.core.common.util;

import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.Collection;

public class RestTemplateUtil {
    //追加参数
    public static String appendParam(String urlSource, Collection<String> paraNames) {
        if (paraNames == null || paraNames.isEmpty()) {
            return urlSource;
        }
        StringBuilder sb = new StringBuilder(urlSource);
        if (StringUtils.contains(urlSource, "?")) {
            //原来已经有参数了
            if (!StringUtils.endsWith(urlSource, "?")) {
                //不是末尾有个问号。那么就加个 & 符号
                sb.append("&");
            }
        } else {
            //还没有参数
            sb.append("?");
        }

        for (String key : paraNames) {
            sb.append(key).append("=").append("{").append(key).append("}");
            sb.append("&");
        }
        String s = sb.toString();
        if (StringUtils.endsWith(s, "&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;

    }


    public static String buildRestTemplateUrl(String host, int port, String path, Collection<String> paraNames) {
        if (port == 80) {
            return buildRestTemplateUrl("http", host, port, path, paraNames);
        }
        if (port == 443) {
            return buildRestTemplateUrl("https", host, port, path, paraNames);
        }
        throw new RuntimeException("无法猜测到schema");
    }

    public static String buildRestTemplateUrl(String schema, String host, int port, String path, Collection<String> paraNames) {
        StringBuilder schemaHostPort = new StringBuilder();
        schemaHostPort.append(schema).append("://");
        schemaHostPort.append(host).append(":").append(port);
        return buildRestTemplateUrl(schemaHostPort.toString(), path, paraNames);
    }

    public static String buildRestTemplateUrl(String schemaHostPort, String path, Collection<String> paraNames) {
        StringBuilder url = new StringBuilder();
        url.append(schemaHostPort);
        if (org.apache.commons.lang3.StringUtils.startsWith(path, "/")) {
            url.append(path);
        } else {
            url.append("/").append(path);
        }
        return appendParam(url.toString(), paraNames);
    }


}
