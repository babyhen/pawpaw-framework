package com.pawpaw.framework.core.test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.pawpaw.framework.core.common.IEnumType;
import com.pawpaw.framework.core.web.IEnumTypeJsonSerializer;
import com.pawpaw.framework.core.web.RemoteResult;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ObjectMapperTest {


    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleModule module = new SimpleModule();
        module.addSerializer(IEnumType.class, new IEnumTypeJsonSerializer());
        om.registerModule(module);

        List<Long> data = new LinkedList<>();
        data.add(1L);
        RemoteResult o = new RemoteResult(new Date());
        String s = om.writeValueAsString(o);
        System.out.println(s);
    }


}




