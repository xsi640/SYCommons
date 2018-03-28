package com.poreader.common.cache;

import java.util.List;

public interface StaticCache<TKey, TValue> {
	int size();

	List<TValue> all();

	void add(TKey key, TValue value);

	void remove(TKey key);
	
	TValue get(TKey key);
	
	void clear();
}
