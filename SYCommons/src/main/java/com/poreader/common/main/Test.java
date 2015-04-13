package com.poreader.common.main;

import com.poreader.common.cache.StandardHotCache;

public class Test {
	public static void main(String[] args) {
		StandardHotCache<String, String> cache = new StandardHotCache<String, String>();
		cache.setDetectSpanInSecs(1);
		cache.setMaxMuteSpanInSecs(10);
		cache.initialize();
		cache.add("1", "suyang");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(cache.get("1"));
	}
}
