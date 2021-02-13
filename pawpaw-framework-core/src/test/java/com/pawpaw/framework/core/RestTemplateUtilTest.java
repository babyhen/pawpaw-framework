package com.pawpaw.framework.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pawpaw.framework.core.common.util.RestTemplateUtil;
import com.pawpaw.framework.core.common.util.TimeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.pawpaw.framework.core.common.util.TimeUtil.TIME_FORMAT_19;

public class RestTemplateUtilTest {


    @Test
    public void test() throws JsonProcessingException {
        String s = "http://abc.com/xxx?x=1";
        List<String> p = new LinkedList<>();
        p.add("a");
        p.add("b");
        p.add("c");
        String url = RestTemplateUtil.appendParam(s, p);
        System.out.println(url);
    }
}




