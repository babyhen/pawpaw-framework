package com.pawpaw.framework.core.common.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

public class PawpawFrameworkUtil {

    public static boolean isPawpawPackageClass(String className) {
        return StringUtils.startsWith(className, "com.pawpaw");
    }

    public static boolean isPawpawPackageClass(Type type) {
        return isPawpawPackageClass(type.getTypeName());
    }

}
