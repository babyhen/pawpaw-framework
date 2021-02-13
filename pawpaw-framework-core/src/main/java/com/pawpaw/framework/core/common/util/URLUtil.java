package com.pawpaw.framework.core.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class URLUtil {

    /**
     * 向已有的url追加参数
     *
     * @param valueNeedEncode 是否需要urlencode 值。考虑某些特殊情况下不需要额外的urlencode
     */

    public static String appendParam(String urlSource, Map<String, String> keyValuePairs, boolean valueNeedEncode) {
        if (keyValuePairs == null || keyValuePairs.isEmpty()) {
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
        for (String key : keyValuePairs.keySet()) {
            sb.append(key).append("=");
            String rawValue = keyValuePairs.get(key);
            if (valueNeedEncode) {
                try {
                    sb.append(URLEncoder.encode(rawValue, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                sb.append(rawValue);
            }
            sb.append("&");
        }
        String s = sb.toString();
        if (org.apache.commons.lang3.StringUtils.endsWith(s, "&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;

    }

}
