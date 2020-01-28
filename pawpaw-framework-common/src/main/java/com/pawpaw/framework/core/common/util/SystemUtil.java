package com.pawpaw.framework.core.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author liujixin
 * @date 2019-01-27
 * @description
 */
public class SystemUtil {
    public static String getIPOrHostName() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            //swallow
        }
        //获取本机ip
        String ip = addr.getHostAddress();
        if (ip != null && !ip.equals("")) {
            return ip;

        }
        //获取本机计算机名称
        String hostName = addr.getHostName();
        if (hostName != null && !hostName.equals("")) {
            return hostName;
        }
        throw new RuntimeException("获取本机ip失败");
    }

}
