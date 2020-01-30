package com.pawpaw.framework.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pawpaw.framework.core.common.util.TimeUtil;
import org.junit.Test;

import java.util.Date;

import static com.pawpaw.framework.core.common.util.TimeUtil.TIME_FORMAT_8;

public class TimeUtilTest {


    @Test
    public void parse() throws JsonProcessingException {
        Date time = TimeUtil.parse("20200128", TIME_FORMAT_8);
        System.out.println(time);
    }

    @Test
    public void minusYear() throws JsonProcessingException {
        Date time = TimeUtil.minusYear(new Date(), -1);
        System.out.println(time);
    }


}




