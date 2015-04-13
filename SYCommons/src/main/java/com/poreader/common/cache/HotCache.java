package com.poreader.common.cache;

import java.util.List;

public interface HotCache<TKey, TValue> {

	void setDetectSpanInSecs(int detectSpanInSecs);

	int getDetectSpanInSecs();

	void setMaxMuteSpanInSecs(int maxMuteSpanInSecs);

	int getMaxMuteSpanInSecs();

	void initialize();
	
	int size();

	List<TValue> all();

	void add(TKey key, TValue value);

	void remove(TKey key);
	
	TValue get(TKey key);
	
	void clear();
}
