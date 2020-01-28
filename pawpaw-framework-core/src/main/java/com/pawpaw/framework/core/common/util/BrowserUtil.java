package com.pawpaw.framework.core.common.util;

/**
 * @author liujixin
 * @date 2020-01-28
 * @description
 */
public class BrowserUtil {


    public static void openBrownse(String url) {
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                // 创建一个URI实例
                java.net.URI uri = java.net.URI.create(url);
                // 获取当前系统桌面扩展
                java.awt.Desktop dp = java.awt.Desktop.getDesktop();
                // 判断系统桌面是否支持要执行的功能
                if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                    // 获取系统默认浏览器打开链接
                    dp.browse(uri);
                } else {
                    throw new RuntimeException("this system do not support opening browser");
                }

            } catch (Exception e) {
                throw new RuntimeException("open browser fail，" + e.getMessage());
            }
        } else {
            throw new RuntimeException("this system do not support opening browser");
        }
    }

}
