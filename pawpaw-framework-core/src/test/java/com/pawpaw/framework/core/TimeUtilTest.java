package com.pawpaw.framework.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pawpaw.framework.core.common.util.TimeUtil;
import org.junit.Test;

import java.util.Date;

import static com.pawpaw.framework.core.common.util.TimeUtil.TIME_FORMAT_19;
import static com.pawpaw.framework.core.common.util.TimeUtil.TIME_FORMAT_8;

public class TimeUtilTest {


    @Test
    public void parse() throws JsonProcessingException {
        Date time = TimeUtil.parseDateTime("2020-01-20 10:11:12", TIME_FORMAT_19);
        System.out.println(time);
    }

    @Test
    public void minusYear() throws JsonProcessingException {
        Date time = TimeUtil.minusYear(new Date(), -1);
        System.out.println(time);
    }

    @Test
    public void midnightTime() throws JsonProcessingException {
        Date time = TimeUtil.midnightTime(new Date());
        System.out.println(time);
    }

    @Test
    public void lastTimeOfDay() throws JsonProcessingException {
        Date time = TimeUtil.lastTimeOfDay(new Date());
        System.out.println(time);
    }
}




