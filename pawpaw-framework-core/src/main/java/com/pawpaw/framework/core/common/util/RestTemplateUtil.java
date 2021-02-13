package com.pawpaw.framework.core.common.util;

import org.apache.commons.lang3.StringUtils;

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

    public static String buildRestTemplateUrl(String hostPort, String path, Collection<String> paraNames) {
        return buildRestTemplateUrl("http", hostPort, path, paraNames);
    }

    public static String buildRestTemplateUrl(String schema, String hostPort, String path, Collection<String> paraNames) {
        StringBuilder url = new StringBuilder();
        url.append(schema).append("://");
        url.append(hostPort);
        if (org.apache.commons.lang3.StringUtils.startsWith(path, "/")) {
            url.append(path);
        } else {
            url.append("/").append(path);
        }
        if (paraNames == null || paraNames.isEmpty()) {
            return url.toString();
        } else {
            url.append("?");
            for (String key : paraNames) {
                url.append(key).append("=").append("{").append(key).append("}");
                url.append("&");
            }
            String s = url.toString();
            if (StringUtils.endsWith(s, "&")) {
                s = StringUtils.substringBeforeLast(s, "&");
            }
            return s;
        }

    }

}
