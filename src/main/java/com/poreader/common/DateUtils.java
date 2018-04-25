package com.poreader.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Yang
 */
public class DateUtils {

    /**
     * 空白日期，定义为1900-1-1
     */
    public final static Date Date_EMPTY = new Date() {

        private static final long serialVersionUID = 8729434191329587401L;

        {
            Calendar c = Calendar.getInstance();
            c.clear();
            c.set(1900, 1, 1);
            super.setTime(c.getTime().getTime());
        }
    };

    public final static Date DATE_MAX = new Date(){
        {
            Calendar c = Calendar.getInstance();
            c.clear();
            c.set(9999,12,31);
            super.setTime(c.getTime().getTime());
        }
    };

    /**
     * 字符串转Date类型
     *
     * @param str
     * @param defaultVal
     * @param pattern
     * @return
     */
    public static Date formString(String str, Date defaultVal, String pattern) {
        Date result = defaultVal;
        if (pattern == null || pattern.isEmpty())
            pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            result = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 按指定日期类型转换成字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String toString(Date date, String pattern) {
        if (pattern == null || pattern.isEmpty())
            pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static Calendar toCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static Date toDate(Calendar calendar) {
        return calendar.getTime();
    }

    public static Long getTotalMilliseconds(Date min, Date max) {
        long lMin = min.getTime();
        long lMax = max.getTime();
        return lMax - lMin;
    }

    public static long getTotalSeconds(Date min, Date max) {
        long milliseconds = getTotalMilliseconds(min, max);
        return milliseconds / 1000;
    }

    public static long getTotalMinutes(Date min, Date max) {
        long milliseconds = getTotalMilliseconds(min, max);
        return milliseconds / (60 * 1000);
    }

    public static long getTotalHours(Date min, Date max) {
        long milliseconds = getTotalMilliseconds(min, max);
        return milliseconds / (60 * 60 * 1000);
    }

    public static long getTotalDays(Date min, Date max) {
        long milliseconds = getTotalMilliseconds(min, max);
        return milliseconds / (24 * 60 * 60 * 1000);
    }

    public static long getTotalMonth(Date min, Date max) {
        LocalDate minLocalDate = getLocalDate(min);
        LocalDate maxLocalDate = getLocalDate(max);

        Period period = Period.between(maxLocalDate, minLocalDate);
        return period.toTotalMonths();
    }

    public static long getTotalWeek(Date min, Date max) {
        long totalDay = getTotalDays(min, max);
        return totalDay / 7;
    }

    public static long getTotalYear(Date min, Date max) {
        LocalDate minLocalDate = getLocalDate(min);
        LocalDate maxLocalDate = getLocalDate(max);

        Period period = Period.between(maxLocalDate, minLocalDate);
        return period.getYears();
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static LocalDate getLocalDate(java.util.Date date, ZoneId zone) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(zone);
        return zdt.toLocalDate();
    }

    public static LocalDate getLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        return zdt.toLocalDate();
    }

    public static Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.MINUTE, minute);

        return calendar.getTime();
    }

    public static Date addMinute(int minute) {
        return addMinute(new Date(), minute);
    }

    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_YEAR, day);

        return calendar.getTime();
    }

    public static Date addDay(int day) {
        return addDay(new Date(), day);
    }

    public static Date addMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.MONTH, month);

        return calendar.getTime();
    }

    public static Date addMonth(int month) {
        return addMonth(new Date(), month);
    }

    public static Date addYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);

        return calendar.getTime();
    }

    public static Date addYear(int year) {
        return addYear(new Date(), year);
    }
}
