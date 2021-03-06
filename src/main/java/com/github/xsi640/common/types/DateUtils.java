package com.github.xsi640.common.types;

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

    /**
     * 最大日期，定义为9999-12-31
     */
    public final static Date DATE_MAX = new Date() {
        {
            Calendar c = Calendar.getInstance();
            c.clear();
            c.set(9999, 12, 31);
            super.setTime(c.getTime().getTime());
        }
    };

    /**
     * 字符串转Date类型
     *
     * @param str        要转换Date类型的字符串
     * @param defaultVal 转换失败时，使用的默认值
     * @param pattern    日期的格式
     * @return 转换后的Date
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
     * @param date    要转换字符串的Date
     * @param pattern 转换的格式，（默认：yyyy-MM-dd HH:mm:ss）
     * @return 转换后的字符串
     */
    public static String toString(Date date, String pattern) {
        if (pattern == null || pattern.isEmpty())
            pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 将Data转换成Calendar
     *
     * @param date 要转换的Date
     * @return 转换后的Calendar
     */
    public static Calendar toCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * 将Calendar转换成Date
     *
     * @param calendar 要转换的Calendar
     * @return 转换后的Date
     */
    public static Date toDate(Calendar calendar) {
        return calendar.getTime();
    }

    /**
     * 将Date类型转换成LocalDate
     *
     * @param date 要转换的Date
     * @param zone 时区
     * @return 转换后的LocalDate
     */
    public static LocalDate toLocalDate(java.util.Date date, ZoneId zone) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(zone);
        return zdt.toLocalDate();
    }

    /**
     * 将Date类型转换成LocalDate，使用系统默认时区
     *
     * @param date 要转换的Date
     * @return 转换后的LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        return toLocalDate(date, ZoneId.systemDefault());
    }

    /**
     * 获取两个时间差的毫秒数
     *
     * @param min 最小时间
     * @param max 最大时间
     * @return 毫秒数
     */
    public static Long getTotalMilliseconds(Date min, Date max) {
        long lMin = min.getTime();
        long lMax = max.getTime();
        return lMax - lMin;
    }

    /**
     * 获取两个时间差的秒数
     *
     * @param min 最小时间
     * @param max 最大时间
     * @return 秒数
     */
    public static long getTotalSeconds(Date min, Date max) {
        long milliseconds = getTotalMilliseconds(min, max);
        return milliseconds / 1000;
    }

    /**
     * 获取两个时间差的分钟数
     *
     * @param min 最小时间
     * @param max 最大时间
     * @return 分钟数
     */
    public static long getTotalMinutes(Date min, Date max) {
        long milliseconds = getTotalMilliseconds(min, max);
        return milliseconds / (60 * 1000);
    }

    /**
     * 获取两个时间差的小时数
     *
     * @param min 最小时间
     * @param max 最大时间
     * @return 小时数
     */
    public static long getTotalHours(Date min, Date max) {
        long milliseconds = getTotalMilliseconds(min, max);
        return milliseconds / (60 * 60 * 1000);
    }

    /**
     * 获取两个时间差的天数
     *
     * @param min 最小时间
     * @param max 最大时间
     * @return 天数
     */
    public static long getTotalDays(Date min, Date max) {
        long milliseconds = getTotalMilliseconds(min, max);
        return milliseconds / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取两个时间差的月数
     *
     * @param min 最小时间
     * @param max 最大时间
     * @return 月数
     */
    public static long getTotalMonth(Date min, Date max) {
        LocalDate minLocalDate = toLocalDate(min);
        LocalDate maxLocalDate = toLocalDate(max);

        Period period = Period.between(maxLocalDate, minLocalDate);
        return period.toTotalMonths();
    }

    /**
     * 获取两个时间差的周数
     *
     * @param min 最小时间
     * @param max 最大时间
     * @return 周数
     */
    public static long getTotalWeek(Date min, Date max) {
        long totalDay = getTotalDays(min, max);
        return totalDay / 7;
    }

    /**
     * 获取两个时间差的年数
     *
     * @param min 最小时间
     * @param max 最大时间
     * @return 年数
     */
    public static long getTotalYear(Date min, Date max) {
        LocalDate minLocalDate = toLocalDate(min);
        LocalDate maxLocalDate = toLocalDate(max);

        Period period = Period.between(maxLocalDate, minLocalDate);
        return period.getYears();
    }

    /**
     * 指定Date增加日期
     *
     * @param date   要增加的Date
     * @param unit   单位 Calendar.SECOND
     * @param number 数量
     * @return 增加后的Date
     */
    public static Date addCalendar(Date date, int unit, int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(unit, number);
        return calendar.getTime();
    }

    /**
     * 当前时间增加日期
     *
     * @param unit   单位 如：Calendar.SECOND
     * @param number 数量
     * @return 增加后的Date
     */
    public static Date addCalendar(int unit, int number) {
        return addCalendar(new Date(), unit, number);
    }

    /**
     * 指定Date增加秒数
     *
     * @param date   要增加的Date
     * @param second 增加的秒数
     * @return 增加后的Date
     */
    public static Date addSecond(Date date, int second) {
        return addCalendar(date, Calendar.SECOND, second);
    }

    /**
     * 当前Date增加增加秒数
     *
     * @param second 增加的秒数
     * @return 增加后的Date
     */
    public static Date addSecond(int second) {
        return addSecond(new Date(), second);
    }

    /**
     * 指定Date增加分钟数
     *
     * @param date   要增加的Date
     * @param minute 增加的分钟数
     * @return 增加后的Date
     */
    public static Date addMinute(Date date, int minute) {
        return addCalendar(date, Calendar.MINUTE, minute);
    }

    /**
     * 当前Date增加分钟数
     *
     * @param minute 增加的分钟数
     * @return 增加后的Date
     */
    public static Date addMinute(int minute) {
        return addMinute(new Date(), minute);
    }

    /**
     * 指定Date增加天数
     *
     * @param date 要增加的Date
     * @param day  增加的天数
     * @return 增加后的Date
     */
    public static Date addDay(Date date, int day) {
        return addCalendar(date, Calendar.DAY_OF_YEAR, day);
    }

    /**
     * 当前Date添加天数
     *
     * @param day 增加的天数
     * @return 增加后的Date
     */
    public static Date addDay(int day) {
        return addDay(new Date(), day);
    }

    /**
     * 指定Date增加月数
     *
     * @param date  要增加的Date
     * @param month 增加的月数
     * @return 增加后的Date
     */
    public static Date addMonth(Date date, int month) {
        return addCalendar(date, Calendar.MONTH, month);
    }

    /**
     * 当前Date增加月数
     *
     * @param month 增加的月数
     * @return 增加后的Date
     */
    public static Date addMonth(int month) {
        return addMonth(new Date(), month);
    }

    /**
     * 指定Date增加年数
     *
     * @param date 要增加的Date
     * @param year 增加的年数
     * @return 增加后的Date
     */
    public static Date addYear(Date date, int year) {
        return addCalendar(date, Calendar.YEAR, year);
    }

    /**
     * 当前Date增加年数
     *
     * @param year 增加的年数
     * @return 增加后的Date
     */
    public static Date addYear(int year) {
        return addYear(new Date(), year);
    }

    /**
     * 获得指定Date，在当天的最小时间(00:00:00.000)
     *
     * @param date 指定的的Date
     * @return 当天的最小时间
     */
    public static Date getMinDateOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获得当天的最大时间（23:59:59.999）
     *
     * @param date 指定的的Date
     * @return 当天的最大时间
     */
    public static Date getMaxDateOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }
}
