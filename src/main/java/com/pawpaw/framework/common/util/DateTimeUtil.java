package com.pawpaw.framework.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

public class DateTimeUtil {

    public static DateTimeFormatter TIME_FORMAT_YEAR_MONTH = new DateTimeFormatterBuilder().appendPattern("yyyyMM")
            .toFormatter();

    public static DateTimeFormatter TIME_FORMAT_8 = new DateTimeFormatterBuilder().appendPattern("yyyyMMdd")
            .toFormatter();

    public static DateTimeFormatter TIME_FORMAT_14 = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss")
            .toFormatter();

    public static DateTimeFormatter TIME_FORMAT_19 = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss")
            .toFormatter();

    /**
     * 返回 yyyyMMddHHmmss。例如： 20170412173155
     *
     * @param million
     * @return
     */
    public static String format(long million, DateTimeFormatter format) {

        Instant instant = Instant.ofEpochMilli(million);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime dt = LocalDateTime.ofInstant(instant, zone);
        return format.format(dt);
    }

    public static String format(Date time, DateTimeFormatter format) {
        return format(time.getTime(), format);
    }

    public static String format19(Date time) {
        return format(time, TIME_FORMAT_19);
    }

    public static String format14(Date time) {
        return format(time, TIME_FORMAT_14);
    }

    public static String format8(Date time) {
        return format(time, TIME_FORMAT_8);
    }

    public static Date parse(String str, DateTimeFormatter format) {

        LocalDateTime dt = LocalDateTime.from(format.parse(str));
        ZonedDateTime zdt = dt.atZone(ZoneOffset.systemDefault());
        Date d = Date.from(zdt.toInstant());
        return d;
    }

    public static Date plus(Date time, long millions) {
        long t = time.getTime() + millions;
        return new Date(t);
    }

    public static Date plusDay(Date time, int day) {
        return plus(time, day * 24 * 60 * 60 * 1000);
    }

    public static Date minus(Date time, long millions) {
        long t = time.getTime() - millions;
        return new Date(t);
    }

    public static Date minusDay(Date time, int day) {
        long mill = day * 24 * 60 * 60 * 1000L;
        return minus(time, mill);
    }

    /**
     * 计算两个日期差多少天，与时间无关。计算的是自然日
     */
    public static int dayInterval(Date start, Date end) {
        LocalDate s = LocalDate.from(start.toInstant());
        LocalDate e = LocalDate.from(end.toInstant());
        return Period.between(s, e).getDays();
    }

    /**
     * 根据时间，得到时间戳
     */
    public static long getTimestamp(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

}
