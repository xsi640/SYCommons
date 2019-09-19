package com.github.xsi640.common.types;

import java.util.Date;
import java.util.UUID;

/**
 * 类型转换辅助类
 */
public class TypeParse {

    /**
     * 字符串转换成Int，转换失败会返回默认值
     *
     * @param str        字符串
     * @param defaultVal 默认值
     * @return
     */
    public static Integer toInt(String str, Integer defaultVal) {
        Integer result = defaultVal;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转换成Int，转换失败会返回0
     *
     * @param str 字符串
     * @return
     */
    public static Integer toInt(String str) {
        return toInt(str, 0);
    }

    /**
     * Object转换成Int，转换失败会返回0
     * @param obj
     * @return
     */
    public static Integer toInt(Object obj) {
        return toInt(obj, 0);
    }

    /**
     * Object转换成Int，转换失败会返回默认值
     * @param obj 字符串
     * @param defaultVal 默认值
     * @return
     */
    public static Integer toInt(Object obj, Integer defaultVal) {
        return toInt(obj.toString(), defaultVal);
    }

    /**
     * 字符串转换成Long，转换失败会返回默认值
     * @param str
     * @param defaultVal
     * @return
     */
    public static Long toLong(String str, Long defaultVal) {
        Long result = defaultVal;
        try {
            result = Long.parseLong(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转换成Long，转换失败会返回0L
     * @param str
     * @return
     */
    public static Long toLong(String str) {
        return toLong(str, 0l);
    }

    /**
     * Object转换成Long，转换失败会返回0L
     * @param obj
     * @return
     */
    public static Long toLong(Object obj) {
        return toLong(obj, 0l);
    }

    /**
     * Object转换成Long，转换失败会返回默认值
     * @param obj
     * @param defaultVal
     * @return
     */
    public static Long toLong(Object obj, Long defaultVal) {
        return toLong(obj.toString(), defaultVal);
    }

    /**
     * 字符串转换成Boolean，转换失败会返回默认值
     * @param str
     * @param defaultVal
     * @return
     */
    public static Boolean toBoolean(String str, Boolean defaultVal) {
        Boolean result = defaultVal;
        try {
            result = Boolean.parseBoolean(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转换成Boolean，转换失败会返回false
     * @param str
     * @return
     */
    public static Boolean toBoolean(String str) {
        return toBoolean(str, false);
    }

    /**
     * Object转换成Boolean，转换失败会返回false
     * @param obj
     * @return
     */
    public static Boolean toBoolean(Object obj) {
        return toBoolean(obj, false);
    }

    /**
     * Object转换成Boolean，转换失败会返回默认值
     * @param obj
     * @param defaultVal
     * @return
     */
    public static Boolean toBoolean(Object obj, Boolean defaultVal) {
        return toBoolean(obj.toString(), defaultVal);
    }

    /**
     * 字符串转换成Float，转换失败会返回默认值
     * @param str
     * @param defaultVal
     * @return
     */
    public static Float toFloat(String str, Float defaultVal) {
        Float result = defaultVal;
        try {
            result = Float.parseFloat(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转换成Boolean，转换失败会返回0f
     * @param str
     * @return
     */
    public static Float toFloat(String str) {
        return toFloat(str, 0f);
    }

    /**
     * Object转换成Boolean，转换失败会返回0f
     * @param obj
     * @return
     */
    public static Float toFloat(Object obj) {
        return toFloat(obj, 0f);
    }

    /**
     * Object转换成Boolean，转换失败会返回默认值
     * @param obj
     * @param defaultVal
     * @return
     */
    public static Float toFloat(Object obj, Float defaultVal) {
        return toFloat(obj.toString(), defaultVal);
    }

    /**
     * 字符串转换成Double，转换失败会返回默认值
     * @param str
     * @param defaultVal
     * @return
     */
    public static Double toDouble(String str, Double defaultVal) {
        Double result = defaultVal;
        try {
            result = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转换成Double，转换失败会返回0d
     * @param str
     * @return
     */
    public static Double toDouble(String str) {
        return toDouble(str, 0d);
    }

    /**
     * Object转换成Double，转换失败会返回默认值
     * @param obj
     * @param defaultVal
     * @return
     */
    public static Double toDouble(Object obj, Double defaultVal) {
        return toDouble(obj.toString(), defaultVal);
    }

    /**
     * Object转换成Double，转换失败会返回0d
     * @param obj
     * @return
     */
    public static Double toDouble(Object obj) {
        return toDouble(obj, 0d);
    }

    /**
     * 字符串转换成Short，转换失败会返回默认值
     * @param str
     * @param defaultVal
     * @return
     */
    public static Short toShort(String str, Short defaultVal) {
        Short result = defaultVal;
        try {
            result = Short.parseShort(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转换成Short，转换失败会返回0
     * @param str
     * @return
     */
    public static Short toShort(String str) {
        return toShort(str, (short) 0);
    }

    /**
     * Object转换成Short，转换失败会返回默认值
     * @param obj
     * @param defaultVal
     * @return
     */
    public static Short toShort(Object obj, Short defaultVal) {
        return toShort(obj.toString(), defaultVal);
    }

    /**
     * Object转换成Short，转换失败会返回0
     * @param obj
     * @return
     */
    public static Short toShort(Object obj) {
        return toShort(obj, (short) 0);
    }

    /**
     * 字符串转换成Byte，转换失败会返回默认值
     * @param str
     * @param defaultVal
     * @return
     */
    public static Byte toByte(String str, Byte defaultVal) {
        Byte result = defaultVal;
        try {
            result = Byte.parseByte(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转换成Byte，转换失败会返回0
     * @param str
     * @return
     */
    public static Byte toByte(String str) {
        return toByte(str, (byte) 0);
    }

    /**
     * Object转换成Byte，转换失败会返回默认值
     * @param obj
     * @param defaultVal
     * @return
     */
    public static Byte toByte(Object obj, Byte defaultVal) {
        return toByte(obj.toString(), defaultVal);
    }

    /**
     * Object转换成Byte，转换失败会返回0
     * @param obj
     * @return
     */
    public static Byte toByte(Object obj) {
        return toByte(obj, (byte) 0);
    }

    /**
     * 字符串转换成UUID，转换失败会返回默认值
     * @param str
     * @param defaultVal
     * @return
     */
    public static UUID toUUID(String str, UUID defaultVal) {
        UUID result = defaultVal;
        try {
            result = UUIDUtils.fromString(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转换成UUID，转换失败会返回UUID_EMPTY
     * @param str
     * @return
     */
    public static UUID toUUID(String str) {
        return toUUID(str, UUIDUtils.UUID_EMPTY);
    }

    /**
     * Object转换成UUID，转换失败会返回默认值
     * @param obj
     * @param defaultVal
     * @return
     */
    public static UUID toUUID(Object obj, UUID defaultVal) {
        return toUUID(obj.toString(), defaultVal);
    }

    /**
     * Object转换成UUID，转换失败会返回UUID_EMPTY
     * @param obj
     * @return
     */
    public static UUID toUUID(Object obj) {
        return toUUID(obj, UUIDUtils.UUID_EMPTY);
    }

    /**
     * 字符串转换成Date，转换失败会返回默认值
     * @param str
     * @param defaultVal
     * @param pattern 日期时间格式
     * @return
     */
    public static Date toDate(String str, Date defaultVal, String pattern) {
        return DateUtils.formString(str, defaultVal, pattern);
    }

    /**
     * 字符串转换成Date（日期格式：yyyy-MM-dd HH:mm:ss），转换失败会返回默认值
     * @param str
     * @param defaultVal
     * @return
     */
    public static Date toDate(String str, Date defaultVal) {
        return toDate(str, defaultVal, null);
    }

    /**
     * 字符串转换成Date（日期格式：yyyy-MM-dd HH:mm:ss），转换失败会返回1900-1-1
     * @param str
     * @return
     */
    public static Date toDate(String str) {
        return toDate(str, DateUtils.Date_EMPTY);
    }

    /**
     * 字符串转换成Date，转换失败返回1900-1-1
     * @param str
     * @param pattern
     * @return
     */
    public static Date toDate(String str, String pattern) {
        return DateUtils.formString(str, DateUtils.Date_EMPTY, pattern);
    }

    /**
     * Object转换成Date，转换失败返回默认值
     * @param obj
     * @param defaultVal
     * @param pattern 日期时间格式
     * @return
     */
    public static Date toDate(Object obj, Date defaultVal, String pattern) {
        return toDate(obj.toString(), defaultVal, pattern);
    }

    /**
     * Object转换成Date（日期格式：yyyy-MM-dd HH:mm:ss），转换失败返回1900-1-1
     * @param obj
     * @param defaultVal
     * @return
     */
    public static Date toDate(Object obj, Date defaultVal) {
        return toDate(obj, defaultVal);
    }

    /**
     * Object转换成Date（日期格式：yyyy-MM-dd HH:mm:ss），转换失败返回1900-1-1
     * @param obj
     * @return
     */
    public static Date toDate(Object obj) {
        return toDate(obj, DateUtils.Date_EMPTY);
    }
}
