package com.github.xsi640.common;

import com.github.xsi640.common.encode.EncodingUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 字节转换工具类
 * @author SuYang
 *
 */
public class BitConverter {

	/**
	 * boolean 转换成 byte
	 * @param value 要转换的boolean
	 * @return 转换完成的byte[]
	 */
	public static byte getBytes(boolean value) {
		return (byte) (value ? 1 : 0);
	}
	
	/**
	 * byte 转换成 boolean
	 * @param b 要转换的byte
	 * @return 转换完成的boolean
	 */
	public static boolean toBoolean(byte b) {
		if(b == 1) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * short 转换成 byte[]
	 * @param value 要转换的short
	 * @return 转换完成的byte[]
	 */
	public static byte[] getBytes(short value) {
		byte[] result = new byte[2];
		result[0] = (byte) (value >> 8);
		result[1] = (byte) (value /* >> 0 */);
		return result;
	}
	
	/**
	 * byte[] 转换成 short
	 * @param bytes 要转换的byte[]
	 * @return 转换完成的short
	 */
	public static short toShort(byte[] bytes) {
		return (short) (bytes[0] << 8 | (bytes[1] & 0xFF));
	}
	
	/**
	 * int 转换成 byte[]
	 * @param value 要转换的int
	 * @return 转换完成的byte[]
	 */
	public static byte[] getBytes(int value) {
		byte[] result = new byte[4];
		result[0] = (byte) (value >> 24);
		result[1] = (byte) (value >> 16);
		result[2] = (byte) (value >> 8);
		result[3] = (byte) (value /* >> 0 */);
		return result;
	}
	
	/**
	 * byte[] 转换成 int
	 * @param bytes 要转换的byte[]
	 * @return 转换完成的int
	 */
	public static int toInt(byte[] bytes) {
		return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
	}
	
	/**
	 * long 转换成 byte[]
	 * @param value 要转换的long
	 * @return 转换完成的byte[]
	 */
	public static byte[] getBytes(long value) {
		byte[] result = new byte[8];
		result[0] = (byte) (value >> 56);
		result[1] = (byte) (value >> 48);
		result[2] = (byte) (value >> 40);
		result[3] = (byte) (value >> 32);
		result[4] = (byte) (value >> 24);
		result[5] = (byte) (value >> 16);
		result[6] = (byte) (value >> 8);
		result[7] = (byte) (value /* >> 0 */);
		return result;
	}
	
	/**
	 * byte[] 转换成 long
	 * @param bytes 要转换的byte[]
	 * @return 转换完成的long
	 */
	public static long toLong(byte[] bytes) {
		return bytes[0] << 56 | 
				(bytes[1] & 0xFFL) << 48 | 
				(bytes[2] & 0xFFL) << 40 | 
				(bytes[3] & 0xFFL) << 32| 
				(bytes[4] & 0xFFL) << 24 | 
				(bytes[5] & 0xFFL) << 16 | 
				(bytes[6] & 0xFFL) << 8 | 
				(bytes[7] & 0xFFL);
	}

	/**
	 * float 转换成 byte[]
	 * @param value 要转换的float
	 * @return 转换完成的byte[]
	 */
	public static byte[] getBytes(float value) {
		int bits = Float.floatToIntBits(value);
		byte[] bytes = new byte[4];
		bytes[0] = (byte)(bits & 0xff);
		bytes[1] = (byte)((bits >> 8) & 0xff);
		bytes[2] = (byte)((bits >> 16) & 0xff);
		bytes[3] = (byte)((bits >> 24) & 0xff);
		return bytes;
	}
	
	/**
	 * byte[] 转换成 float
	 * @param bytes 要转换的byte[]
	 * @return 转换完成的float
	 */
	public static float toFloat(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getFloat();
	}

	/**
	 * double 转换成 byte[]
	 * @param value 要转换的double
	 * @return 转换完成的byte[]
	 */
	public static byte[] getBytes(double value) {
		long l = Double.doubleToRawLongBits(value);
	    return new byte[] {
	        (byte)((l >> 56) & 0xff),
	        (byte)((l >> 48) & 0xff),
	        (byte)((l >> 40) & 0xff),
	        (byte)((l >> 32) & 0xff),
	        (byte)((l >> 24) & 0xff),
	        (byte)((l >> 16) & 0xff),
	        (byte)((l >> 8) & 0xff),
	        (byte)((l >> 0) & 0xff),
	    };
	}
	
	/**
	 * byte[] 转换成 double
	 * @param bytes 要转换的byte[]
	 * @return 转换完成的double
	 */
	public static double toDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getDouble();
	}
	
	/**
	 * char 转成 byte[]
	 * @param c 要转换的char
	 * @return 转换完成的byte[]
	 */
	public static byte[] getBytes(char c) {
		byte[] b = new byte[2];
	    b[0] = (byte) ((c & 0xFF00) >> 8);
	    b[1] = (byte) (c & 0xFF);
	    return b;
	}
	
	/**
	 * byte[] 转换成 char
	 * @param bytes 要转换的byte[]
	 * @return 转换完成的char
	 */
	public static char toChar(byte[] bytes) {
		return (char) (((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF));
	}
	
	/**
	 * char[] 转换成 byte[]，UTF-8编码
	 * @param cArray 要转换的char[]
	 * @return 转换完成的byte[]
	 */
	public static byte[] getBytes(char[] cArray) {
		return getBytes(new String(cArray), EncodingUtils.DEFAULT_CHARSET);
	}
	
	/**
	 * char[] 转换成 byte[]，指定编码方式
	 * @param cArray 要转换的char[]
	 * @param charset 字符集
	 * @return 转换完成的byte[]
	 */
	public static byte[] getBytes(char[] cArray, Charset charset) {
		return getBytes(new String(cArray), charset);
	}
	
	/**
	 * byte[] 转换成 char[]，UTF-8编码
	 * @param bytes 要转换的byte[]
	 * @return 转换完成的char[]
	 */
	public static char[] toCharArray(byte[] bytes) {
		return toString(bytes, EncodingUtils.DEFAULT_CHARSET).toCharArray();
	}
	
	/**
	 * byte[] 转换成 char[]，指定编码方式
	 * @param bytes 要转换的byte[]
	 * @param charset 字符集
	 * @return 转换完成的char[]
	 */
	public static char[] toCharArray(byte[] bytes, Charset charset) {
		return toString(bytes, charset).toCharArray();
	}
	
	/**
	 * string 转换成 char[]，UTF-8编码
	 * @param s 要转换的string
	 * @return 转换完成的byte[]
	 */
	public static byte[] getBytes(String s) {
		return getBytes(s, EncodingUtils.DEFAULT_CHARSET);
	}
	
	/**
	 * string 转换成 byte[]，指定编码方式
	 * @param s 要转换的string
	 * @param charset 字符集
	 * @return 转换完成的byte[]
	 */
	public static byte[] getBytes(String s, Charset charset) {
		return s.getBytes(charset);
	}
	
	/**
	 * byte[] 转换成 string，UTF-8编码
	 * @param bytes 要转换的byte[]
	 * @return 转换完成的string
	 */
	public static String toString(byte[] bytes) {
		return toString(bytes, EncodingUtils.DEFAULT_CHARSET);
	}
	
	/**
	 * byte[] 转换成 string，指定编码方式
	 * @param bytes 要转换的byte[]
	 * @param charset 字符集
	 * @return 转换完成的string
	 */
	public static String toString(byte[] bytes, Charset charset) {
		return new String(bytes, charset);
	}
}
