package com.github.xsi640.common.threads;

/**
 * 回调接口
 * @param <T>
 */
public interface Callback<T> {
	/**
	 * 回调方法
	 * @param t
	 */
	void back(T t);
}
