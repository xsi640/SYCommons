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
	
	public TKey getKey() {
		return key;
	}

	public void setKey(TKey key) {
		this.key = key;
	}

	public TValue getValue() {
		return value;
	}

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