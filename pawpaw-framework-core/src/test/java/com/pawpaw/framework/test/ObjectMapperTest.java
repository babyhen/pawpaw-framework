package com.pawpaw.framework.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pawpaw.framework.core.factory.json.ObjectMapperFactory;
import com.pawpaw.framework.core.factory.json.PawpawObjectMapper;
import com.pawpaw.framework.core.web.RemoteResult;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ObjectMapperTest {


    @Test
    public void test() throws JsonProcessingException {
        PawpawObjectMapper om = ObjectMapperFactory.defaultObjectMapper();

        List<Long> data = new LinkedList<>();
        data.add(1L);
        RemoteResult o = new RemoteResult(new Date());
        String s = om.getRawObjectMapper().writeValueAsString(o);
        System.out.println(s);
    }


}




