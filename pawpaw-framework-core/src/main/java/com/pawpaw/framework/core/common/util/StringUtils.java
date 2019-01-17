package com.pawpaw.framework.core.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;


public class StringUtils {

    public static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

    public static List<String> split(String s) {
        return split(s, ",");
    }


    public static List<String> split(String s, String sep) {
        if (s == null) {
            return new LinkedList<>();
        }
        String[] arr = org.springframework.util.StringUtils.split(s, sep);
        return CollectionConventer.array2List(arr);
    }


}
