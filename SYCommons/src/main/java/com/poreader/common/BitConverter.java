package com.poreader.common;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class BitConverter {
	
	private static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	public static byte getBytes(boolean value) {
		return (byte) (value ? 1 : 0);
	}
	
	public static boolean toBoolean(byte b) {
		if(b == 1) {
			return true;
		}else {
			return false;
		}
	}

	public static byte[] getBytes(short value) {
		byte[] result = new byte[2];
		result[0] = (byte) (value >> 8);
		result[1] = (byte) (value /* >> 0 */);
		return result;
	}
	
	public static short toShort(byte[] bytes) {
		return (short) (bytes[0] << 8 | (bytes[1] & 0xFF));
	}
	
	public static byte[] getBytes(int value) {
		byte[] result = new byte[4];
		result[0] = (byte) (value >> 24);
		result[1] = (byte) (value >> 16);
		result[2] = (byte) (value >> 8);
		result[3] = (byte) (value /* >> 0 */);
		return result;
	}
	
	public static int toInt(byte[] bytes) {
		return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
	}
	
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

	public static byte[] getBytes(float value) {
		int bits = Float.floatToIntBits(value);
		byte[] bytes = new byte[4];
		bytes[0] = (byte)(bits & 0xff);
		bytes[1] = (byte)((bits >> 8) & 0xff);
		bytes[2] = (byte)((bits >> 16) & 0xff);
		bytes[3] = (byte)((bits >> 24) & 0xff);
		return bytes;
	}
	
	public static float toFloat(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getFloat();
	}

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
	
	public static double toDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getDouble();
	}
	
	public static byte[] getBytes(char c) {
		byte[] b = new byte[2];
	    b[0] = (byte) ((c & 0xFF00) >> 8);
	    b[1] = (byte) (c & 0xFF);
	    return b;
	}
	
	public static char toChar(byte[] bytes) {
		return (char) (((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF));
	}
	
	public static byte[] getBytes(char[] cArray) {
		return getBytes(new String(cArray), DEFAULT_CHARSET);
	}
	
	public static byte[] getBytes(char[] cArray, Charset charset) {
		return getBytes(new String(cArray), charset);
	}
	
	public static char[] toCharArray(byte[] bytes) {
		return toString(bytes, DEFAULT_CHARSET).toCharArray();
	}
	
	public static char[] toCharArray(byte[] bytes, Charset charset) {
		return toString(bytes, charset).toCharArray();
	}
	
	public static byte[] getBytes(String s) {
		return getBytes(s, DEFAULT_CHARSET);
	}
	
	public static byte[] getBytes(String s, Charset charset) {
		return s.getBytes(charset);
	}
	
	public static String toString(byte[] bytes) {
		return toString(bytes, DEFAULT_CHARSET);
	}
	
	public static String toString(byte[] bytes, Charset charset) {
		return new String(bytes, charset);
	}
}
