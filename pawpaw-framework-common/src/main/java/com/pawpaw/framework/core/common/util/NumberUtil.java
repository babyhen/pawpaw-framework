package com.pawpaw.framework.core.common.util;

public class NumberUtil {

	public static int parseInt(String str, int defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultValue;
		}

	}

	public static int parseInt(String str, String error) {
		if (str == null) {
			throw new NullPointerException(error);
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(error);
		}

	}

}
