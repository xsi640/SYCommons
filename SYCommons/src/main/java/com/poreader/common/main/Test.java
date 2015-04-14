package com.poreader.common.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.poreader.common.HttpUtils;
import com.poreader.common.NameValueCollection;

public class Test {
	public static void main(String[] args) {
		NameValueCollection nvc = new NameValueCollection();
		nvc.put("a", "123");
		System.out.println(HttpUtils.getContent("http://www.baidu.com", nvc, "get"));
	}
}
