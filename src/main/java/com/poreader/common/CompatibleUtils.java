package com.poreader.common;

public class CompatibleUtils {
	/**
	 * 将由c#语言的int转成的字节数组转换成int
	 * @param intBytes
	 * @return
	 */
	public static int csharpIntByteToInt(byte[] intBytes) {
		return (((intBytes[3] & 0xff) << 24) | 
				((intBytes[2] & 0xff) << 16) | 
				((intBytes[1] & 0xff) << 8)  | 
				(intBytes[0] & 0xff));
	}

	/**
	 * 将int转换成c#语言可以识别的字节数组
	 * @param number
	 * @return
	 */
	public static byte[] csharpIntToBytes(int number) {
		byte[] toSendLenBytes = new byte[4];
		toSendLenBytes[0] = (byte) (number & 0xff);
		toSendLenBytes[1] = (byte) ((number >> 8) & 0xff);
		toSendLenBytes[2] = (byte) ((number >> 16) & 0xff);
		toSendLenBytes[3] = (byte) ((number >> 24) & 0xff);
		return toSendLenBytes;
	}
	
	public static long csharpLongByteToLong(byte[] longBytes) {
		return (((longBytes[7] & 0xff) << 64) |
				((longBytes[6] & 0xff) << 56) |
				((longBytes[5] & 0xff) << 48) |
				((longBytes[4] & 0xff) << 40) |
				((longBytes[3] & 0xff) << 32) |
				((longBytes[2] & 0xff) << 16) |
				((longBytes[1] & 0xff) << 8) |
				((longBytes[0] & 0xff)));
	}
}
