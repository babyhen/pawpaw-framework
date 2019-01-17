package com.pawpaw.framework.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 版本比较
 * 
 * @author liujixin
 */
public class VersionCompareUtil {

	/**
	 * 判断版本v1是否大于等于v2<br/>
	 * 版本号是纯数字加上 .分隔
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static final boolean isLargeOrEqual(String version1, String version2) {
		if (StringUtils.isBlank(version1)) {
			return false;
		}
		if (StringUtils.isBlank(version2)) {
			return true;
		}
		String[] versionArray1 = version1.split("\\.");// 注意此处为正则匹配，不能用"."；
		String[] versionArray2 = version2.split("\\.");
		int minLength = Math.min(versionArray1.length, versionArray2.length);// 取最小长度值
		for (int i = 0; i < minLength; i++) {
			String v1 = versionArray1[i];
			String v2 = versionArray2[i];
			int n1 = Integer.parseInt(v1);
			int n2 = Integer.parseInt(v2);
			if (n1 > n2) {
				return true;
			}
			if (n1 < n2) {
				return false;
			}
			// 相等则 continue
		}
		return true;

	}

}
