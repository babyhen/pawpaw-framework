package com.pawpaw.framework.core.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pawpaw.framework.core.common.IEnumType;
import com.pawpaw.framework.core.web.RemoteResult;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ObjectMapperTest {


    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);



        List<Long> data = new LinkedList<>();
        data.add(1L);
        RemoteResult o = new RemoteResult(new Date());
        String s = om.writeValueAsString(o);
        System.out.println(s);
    }


}


