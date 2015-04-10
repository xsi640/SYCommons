package com.poreader.common;

/**
 * 字节大小显示方式
 * @author Yang
 *
 */
public class ByteFormatter {

	private final static float KB = 1024.0f;
	private final static float MB = KB * 1024.0f;
	private final static float GB = MB * 1024.0f;
	private final static float TB = GB * 1024.0f;

	private final static String BFormatPattern = "%s B";
	private final static String KBFormatPattern = "%.2f KB";
	private final static String MBFormatPattern = "%.2f MB";
	private final static String GBFormatPattern = "%.2f GB";
	private final static String TBFormatPattern = "%.2f TB";

	public static String toString(long size) {
		String result = "";
		if (size < KB) {
			result = String.format(BFormatPattern, size);
		} else if (size >= KB && size < MB) {
			result = String.format(KBFormatPattern, size / KB);
		} else if (size >= MB && size < GB) {
			result = String.format(MBFormatPattern, size / MB);
		} else if (size >= GB && size < TB) {
			result = String.format(GBFormatPattern, size / GB);
		} else {
			result = String.format(TBFormatPattern, size / TB);
		}
		return result;
	}

	public static ESizeType getSizeType(long size) {
		ESizeType result = ESizeType.KB;
		if (size < KB) {
			result = ESizeType.B;
		} else if (size >= KB && size < MB) {
			result = ESizeType.KB;
		} else if (size >= MB && size < GB) {
			result = ESizeType.MB;
		} else if (size >= GB && size < TB) {
			result = ESizeType.GB;
		} else {
			result = ESizeType.TB;
		}
		return result;
	}

	public static String toString(long size, ESizeType type) {
		String result = "";
		switch (type) {
		case B:
			result = String.format(BFormatPattern, size);
			break;
		case KB:
			result = String.format(KBFormatPattern, size / KB);
			break;
		case MB:
			result = String.format(MBFormatPattern, size / MB);
			break;
		case GB:
			result = String.format(GBFormatPattern, size / GB);
			break;
		case TB:
			result = String.format(TBFormatPattern, size / TB);
			break;
		default:
			break;
		}
		return result;
	}

	public enum ESizeType {
		B, KB, MB, GB, TB
	}
}
