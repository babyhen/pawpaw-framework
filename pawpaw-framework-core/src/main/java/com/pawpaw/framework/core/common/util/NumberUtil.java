package com.pawpaw.framework.core.common.util;

import java.math.BigDecimal;

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

    public static boolean bigThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == 1;
    }

    public static boolean smallThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == -1;
    }

    public static boolean equalThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == 0;
    }

}
