package com.poreader.common;

import java.util.Arrays;
import java.util.Collection;

public class CollectionUtils {
	public static String toString(Collection<String> collection, String split) {
		String result = "";
		if (collection != null && collection.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (String s : collection) {
				sb.append(s);
				sb.append(split);
			}
			result = sb.substring(0, sb.length() - split.length());
		}
		return result;
	}

	public static Collection<String> toCollection(String text, String split) {
		if (text == null || text.isEmpty())
			return null;
		return Arrays.asList(text.split(split));
	}
}
