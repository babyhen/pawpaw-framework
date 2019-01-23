package com.pawpaw.framework.core.factory.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pawpaw.framework.core.common.IEnumType;
import com.pawpaw.framework.core.factory.json.IEnumTypeJsonSerializer;

/**
 * @author liujixin
 * @date 2019-01-20
 * @description
 */
public class ObjectMapperFactory {


    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        //配置om
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //对IEnumType的支持
        SimpleModule module = new SimpleModule();
        module.addSerializer(IEnumType.class, new IEnumTypeJsonSerializer());
        om.registerModule(module);

        return om;
    }
}
