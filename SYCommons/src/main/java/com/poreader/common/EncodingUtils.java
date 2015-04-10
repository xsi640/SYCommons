package com.poreader.common;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

public class EncodingUtils {
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public static String encode(byte[] data, Charset charset) {
		return new String(data, charset);
	}

	public static String encode(byte[] data) {
		return encode(data, DEFAULT_CHARSET);
	}

	public static byte[] decode(String text, Charset charset) {
		return text.getBytes(charset);
	}

	public static byte[] decode(String text) {
		return decode(text, DEFAULT_CHARSET);
	}

	public static String base64Encode(byte[] data) {
		return Base64.encodeBase64String(data);
	}
	
	public static byte[] base64Decode(String data){
		return Base64.decodeBase64(data);
	}
}
