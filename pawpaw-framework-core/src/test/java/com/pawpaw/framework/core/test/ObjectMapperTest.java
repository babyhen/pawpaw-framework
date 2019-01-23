package com.pawpaw.framework.core.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawpaw.framework.core.factory.json.ObjectMapperFactory;
import com.pawpaw.framework.core.web.RemoteResult;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ObjectMapperTest {


    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper om = ObjectMapperFactory.defaultObjectMapper();

        List<Long> data = new LinkedList<>();
        data.add(1L);
        RemoteResult o = new RemoteResult(new Date());
        String s = om.writeValueAsString(o);
        System.out.println(s);
    }


}




