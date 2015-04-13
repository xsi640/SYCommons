package com.poreader.common.main;

import java.util.concurrent.Callable;

import com.poreader.common.Ref;
import com.poreader.common.ThreadUtils;

public class Test {
	public static void main(String[] args) {
		Ref<String> s = new Ref<String>();
		ThreadUtils.run(new Callable<String>() {
			public String call() throws Exception {
				return "abc";
			}
		}, s);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(s.getValue());
	}
}
