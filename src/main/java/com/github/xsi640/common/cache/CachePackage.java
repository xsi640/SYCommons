package com.github.xsi640.common.cache;

/**
 * 缓存封装包，提供Key，Value，LastAccessTime的包装类
 * @author suyang
 * @param <TKey> 缓存Key
 * @param <TValue> 缓存Value
 */
public class CachePackage<TKey, TValue> {
	private TKey key;
	private TValue value;
	private long lastAccessTime;

	public CachePackage(TKey key, TValue value) {
		this.key = key;
		this.value = value;
		this.lastAccessTime = System.currentTimeMillis();
	}

	/**
	 * 获取缓存Key
	 * @return Key
	 */
	public TKey getKey() {
		return key;
	}

	/**
	 * 设置缓存Key
	 * @param key Key
	 */
	public void setKey(TKey key) {
		this.key = key;
	}

	/**
	 * 获取缓存的值
	 * @return 缓存的值
	 */
	public TValue getValue() {
		return value;
	}

	/**
	 * 设置缓存的值
	 * @param value 缓存的值
	 */
	public void setValue(TValue value) {
		this.value = value;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
}
