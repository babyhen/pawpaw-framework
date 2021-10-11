package com.pawpaw.framework.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pawpaw.common.util.RestTemplateUtil;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class RestTemplateUtilTest {


    @Test
    public void test() throws JsonProcessingException {
        String s = "http://abc.com/xxx?";
        List<String> p = new LinkedList<>();
        p.add("a");
        p.add("b");
        p.add("c");
        String url = RestTemplateUtil.appendParam(s, p);
        System.out.println(url);
        url = RestTemplateUtil.buildRestTemplateUrl("abc.com", 443, "xxx?", p);
        System.out.println(url);
        url = RestTemplateUtil.buildRestTemplateUrl("http","abc.com", 80, "xxx?", p);
        System.out.println(url);
    }
}




