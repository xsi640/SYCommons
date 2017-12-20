package com.poreader.common;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

public class Base64Utils {
	
	private static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	public static String fromBase64(String s) {
		return fromBase64(s, DEFAULT_CHARSET);
	}
	
	public static String fromBase64(String s, Charset charset) {
		if (s == null)
			return "";
		Base64 base64 = new Base64();
		return new String(base64.decode(s.getBytes(charset)), charset);
	}
	
	public static String toBase64(String s) {
		return toBase64(s, DEFAULT_CHARSET);
	}

	public static String toBase64(String s, Charset charset) {
		if (s == null)
			return "";
		Base64 base64 = new Base64();
		return new String(base64.encode(s.getBytes(charset)), charset);
	}
}
