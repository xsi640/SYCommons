package com.poreader.common;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

	public final static int RESPONSE_STREAM_MAX_LENGTH = 3850;
	public final static int MAX_TIME_OUT = 30000;
	public final static String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";
	private final static String DEFAULT_USER_AGENT = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)";

	public static void download(String url, NameValueCollection nvc, String method, String path) {
		Method m = Method.GET;
		if ("GET".equalsIgnoreCase(method)) {
			m = Method.GET;
		} else if ("POST".equalsIgnoreCase(method)) {
			m = Method.POST;
		} else {
			throw new IllegalArgumentException("Http Method Failured.");
		}

		HttpURLConnection conn = null;
		InputStream inputStream = null;
		BufferedOutputStream bufferedOutputStream = null;

		try {
			conn = getConnection(url, nvc, m);
			inputStream = getInputStream(conn, nvc, m);
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
			byte[] buffer = new byte[RESPONSE_STREAM_MAX_LENGTH];
			int read;
			while ((read = inputStream.read(buffer)) != -1) {
				bufferedOutputStream.write(buffer, 0, read);
			}
			bufferedOutputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public static String getContent(String url, NameValueCollection nvc, String method) {
		Method m = Method.GET;
		if ("GET".equalsIgnoreCase(method)) {
			m = Method.GET;
		} else if ("POST".equalsIgnoreCase(method)) {
			m = Method.POST;
		} else {
			throw new IllegalArgumentException("Http Method Failured.");
		}

		HttpURLConnection conn = null;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		String result = "";

		try {
			conn = getConnection(url, nvc, m);
			inputStream = getInputStream(conn, nvc, m);
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
			result = sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return result;
	}

	private static String getParams(NameValueCollection nvc) {
		if (nvc != null && nvc.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (String key : nvc.keySet()) {
				sb.append(EncodingUtils.urlEncode((key)));
				sb.append("=");
				sb.append(EncodingUtils.urlEncode(nvc.get(key)));
				sb.append("&");
			}
			return sb.substring(0, sb.length() - 1);
		}
		return "";
	}

	private static String getUrl(String url, NameValueCollection nvc) {
		if (nvc != null && nvc.size() > 0) {
			StringBuffer sb = new StringBuffer(url);
			sb.append("?");
			for (String key : nvc.keySet()) {
				sb.append(EncodingUtils.urlEncode((key)));
				sb.append("=");
				sb.append(EncodingUtils.urlEncode(nvc.get(key)));
				sb.append("&");
			}
			return sb.substring(0, sb.length() - 1);
		}
		return url;
	}

	public enum Method {
		GET, POST
	}

	class UploadFileItem {
		private String fileName;
		private String filePath;
		private byte[] data;
		private long size;

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		public byte[] getData() {
			return data;
		}

		public void setData(byte[] data) {
			this.data = data;
		}

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}
	}

	private static HttpURLConnection getConnection(String url, NameValueCollection nvc, Method method)
			throws IOException {
		HttpURLConnection result = null;
		if (method == Method.POST) {
			URL u = new URL(url);
			result = (HttpURLConnection) u.openConnection();
			result.setRequestMethod("POST");
		} else {
			URL u = new URL(getUrl(url, nvc));
			result = (HttpURLConnection) u.openConnection();
			result.setRequestMethod("GET");
		}
		result.setRequestProperty("Content-Type", DEFAULT_CONTENT_TYPE);
		result.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
		result.setReadTimeout(MAX_TIME_OUT);
		return result;
	}

	private static InputStream getInputStream(HttpURLConnection conn, NameValueCollection nvc, Method method)
			throws IOException {
		if (method == Method.POST) {
			conn.setDoInput(true);
			conn.setDoOutput(true);
			String param = getParams(nvc);
			if (param != null && !param.isEmpty()) {
				byte[] data = EncodingUtils.decode(param);
				conn.setRequestProperty("Content-Lenth", String.valueOf(data.length));
				OutputStream outputStream = (OutputStream) conn.getOutputStream();
				outputStream.write(data);
				outputStream.flush();
				outputStream.close();
			}
		}
		return conn.getInputStream();
	}
}
