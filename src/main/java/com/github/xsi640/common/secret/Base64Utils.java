package com.github.xsi640.common.secret;

import java.nio.charset.Charset;

import com.github.xsi640.common.encode.EncodingUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * Base64编码工具类
 *
 * @author SuYang
 */
public class Base64Utils {

    /**
     * 将Base64字符串转换为指定的字符串，默认使用UTF-8编码
     *
     * @param s 要转换的Base64字符串
     * @return 转换后的字符串
     */
    public static String fromBase64(String s) {
        return fromBase64(s, EncodingUtils.DEFAULT_CHARSET);
    }

    /**
     * 使用指定编码，将Base64字符串转换为指定的字符串
     *
     * @param s       要转换的Base64字符串
     * @param charset 转换的编码
     * @return 转换后的字符串
     */
    public static String fromBase64(String s, Charset charset) {
        if (StringUtils.isEmpty(s))
            return "";
        return new String(fromBase64ByteArray(s.getBytes(charset)), charset);
    }

    /**
     * 将Base64二进制数组转换为指定的二进制数组
     *
     * @param byteArrays Base64二进制数组
     * @return 转换后的二进制数组
     */
    public static byte[] fromBase64ByteArray(byte[] byteArrays) {
        if (byteArrays == null)
            return null;
        if (byteArrays.length == 0)
            return new byte[]{};
        Base64 base64 = new Base64();
        return base64.decode(byteArrays);
    }

    /**
     * 将指定的字符串转为Base64字符串，使用UTF-8编码
     *
     * @param s 要转换的字符串
     * @return 转换后的Base64字符串
     */
    public static String toBase64(String s) {
        return toBase64(s, EncodingUtils.DEFAULT_CHARSET);
    }

    /**
     * 使用指定编码，将指定的字符串转为Base64字符串
     *
     * @param s       要转换的字符串
     * @param charset 转换的编码
     * @return 转换后的Base64字符串
     */
    public static String toBase64(String s, Charset charset) {
        if (StringUtils.isEmpty(s))
            return "";
        return toBase64(s.getBytes(charset), charset);
    }

    /**
     * 将指定的byte[]转为Base64字符串，使用UTF-8方式编码
     *
     * @param data 要转换的byte[]
     * @return 转换后的Base64字符串
     */
    public static String toBase64(byte[] data) {
        if (data == null)
            return "";
        return toBase64(data, EncodingUtils.DEFAULT_CHARSET);
    }

    /**
     * 将指定的字符串转为Base64字符串
     *
     * @param data    要转换的byte[]
     * @param charset 转换的编码
     * @return 转换后的Base64字符串
     */
    public static String toBase64(byte[] data, Charset charset) {
        if (data == null)
            return "";
        return new String(toBase64ByteArray(data), charset);
    }

    /**
     * 将指定的二进制数组转为Base64二进制数组
     *
     * @param byteArrays 要转换的byte[]
     * @return 转换后的Base64二进制数组
     */
    public static byte[] toBase64ByteArray(byte[] byteArrays) {
        if (byteArrays == null)
            return null;
        if (byteArrays.length == 0)
            return new byte[]{};
        Base64 base64 = new Base64();
        return base64.encode(byteArrays);
    }
}
