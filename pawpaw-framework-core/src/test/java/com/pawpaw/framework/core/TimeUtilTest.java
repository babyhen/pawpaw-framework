package com.pawpaw.framework.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pawpaw.common.util.TimeUtil;
import org.junit.Test;

import java.util.Date;

import static com.pawpaw.common.util.DateTimeUtil.TIME_FORMAT_19;
import static com.pawpaw.common.util.TimeUtil.DAY_HOUR_MINUTE;
import static com.pawpaw.common.util.TimeUtil.HOUR_MINUTE;

public class TimeUtilTest {


    @Test
    public void parse() throws JsonProcessingException {
        Date time = TimeUtil.parseDateTime("2020-01-20 10:11:12", TIME_FORMAT_19);
        System.out.println(time);
    }


    @Test
    public void format() throws JsonProcessingException {
        String hourAndMinute = TimeUtil.format(new Date(), HOUR_MINUTE);
        System.out.println(hourAndMinute);
        String dayHourMinute = TimeUtil.format(new Date(), DAY_HOUR_MINUTE);
        System.out.println(dayHourMinute);
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




