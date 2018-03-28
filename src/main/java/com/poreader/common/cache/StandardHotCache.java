package com.poreader.common.cache;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.poreader.common.engines.StandardCircleEngine;

public class StandardHotCache<TKey, TValue> implements HotCache<TKey, TValue>, Runnable {

	private Hashtable<TKey, CachePackage<TKey, TValue>> hashtable = new Hashtable<TKey, CachePackage<TKey, TValue>>();
	private int detectSpanInSecs = 10;
	private int maxMuteSpanInSecs = 5;
	private StandardCircleEngine standardCircleEngine;

	public void setDetectSpanInSecs(int detectSpanInSecs) {
		this.detectSpanInSecs = detectSpanInSecs;
	}

	public int getDetectSpanInSecs() {
		return detectSpanInSecs;
	}

	public void setMaxMuteSpanInSecs(int maxMuteSpanInSecs) {
		this.maxMuteSpanInSecs = maxMuteSpanInSecs;
	}

	public int getMaxMuteSpanInSecs() {
		return maxMuteSpanInSecs;
	}

	public void initialize() {
		if (this.detectSpanInSecs > 0) {
			this.standardCircleEngine = new StandardCircleEngine(this);
			this.standardCircleEngine.setDetectSpanInSecs(this.detectSpanInSecs);
			this.standardCircleEngine.start();
		}
	}

	public int size() {
		return hashtable.size();
	}

	public List<TValue> all() {
		List<CachePackage<TKey, TValue>> lists = new ArrayList<CachePackage<TKey, TValue>>(hashtable.values());
		List<TValue> result = new ArrayList<TValue>(lists.size());
		for (CachePackage<TKey, TValue> item : lists)
			result.add(item.getValue());
		return result;
	}

	public void add(TKey key, TValue value) {
		this.remove(key);
		this.hashtable.put(key, new CachePackage<TKey, TValue>(key, value));
	}

	public void remove(TKey key) {
		this.hashtable.remove(key);
	}

	public TValue get(TKey key) {
		TValue result = null;
		CachePackage<TKey, TValue> item = this.hashtable.get(key);
		if (item != null)
			result = item.getValue();
		return result;
	}

	public void clear() {
		hashtable.clear();
	}

	public void run() {
		synchronized (hashtable) {
			long now = System.currentTimeMillis();
			List<TKey> keyLists = new ArrayList<TKey>();
			for (CachePackage<TKey, TValue> item : hashtable.values()) {
				long time = now - item.getLastAccessTime();
				if (time > this.maxMuteSpanInSecs * 1000) {
					keyLists.add(item.getKey());
				}
			}
			for (TKey key : keyLists) {
				hashtable.remove(key);
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		if (this.standardCircleEngine != null)
			this.standardCircleEngine.stop();
		super.finalize();
	}

}
