package com.pawpaw.framework.common.aop;

import com.pawpaw.framework.common.util.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public abstract class AbstractDifferentiateMethodParamTypeAdvisor {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDifferentiateMethodParamTypeAdvisor.class);

    public static Map<String, Class<?>> primaryTypeClassMap;

    private static Class<?> getPrimaryTypeClass(String name) {
        AssertUtil.notBlank(name, "����������Ϊ��");
        if (primaryTypeClassMap == null) {
            synchronized (AbstractDifferentiateMethodParamTypeAdvisor.class) {
                if (primaryTypeClassMap == null) {
                    Map<String, Class<?>> map = new HashMap<>();
                    map.put(Byte.TYPE.getName(), Byte.TYPE);
                    map.put(Short.TYPE.getName(), Short.TYPE);
                    map.put(Integer.TYPE.getName(), Integer.TYPE);
                    map.put(Long.TYPE.getName(), Long.TYPE);
                    map.put(Float.TYPE.getName(), Float.TYPE);
                    map.put(Double.TYPE.getName(), Double.TYPE);
                    map.put(Boolean.TYPE.getName(), Boolean.TYPE);
                    map.put(Character.TYPE.getName(), Character.TYPE);
                    map.put(Void.TYPE.getName(), Void.TYPE);
                    primaryTypeClassMap = Collections.unmodifiableMap(map);
                    logger.info("������Ͷ�ӦClass��Map��ʼ���ɹ�");
                }
            }

        }
        Class<?> c = primaryTypeClassMap.get(name);
        return c;
    }

    public Class<?>[] parseMethodParamRealType(JoinPoint joinPoint) throws ClassNotFoundException {
        String longString = joinPoint.getSignature().toLongString();
        logger.debug(longString);
        AssertUtil.notBlank(longString, "longString����Ϊ��");
        int begin = StringUtils.indexOf(longString, "(");
        int end = StringUtils.indexOf(longString, ")");
        if (begin == -1 || end == -1 || begin > end) {
            throw new RuntimeException("jopinPoint��signature�Ƿ�," + longString);
        }
        String params = StringUtils.substring(longString, begin + 1, end);
        String[] typeArr = StringUtils.split(params, ',');
        Class<?>[] types = new Class[typeArr.length];
        for (int i = 0; i < typeArr.length; i++) {
            Class<?> primary = getPrimaryTypeClass(typeArr[i]);
            if (primary != null) {
                types[i] = primary;
            } else {
                types[i] = Class.forName(typeArr[i]);
            }

        }
        return types;
    }

    protected abstract String getAnnotationKey();


}
