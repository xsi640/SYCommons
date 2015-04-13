package com.poreader.common.cache;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class StandardStaticCache<TKey, TValue> implements StaticCache<TKey, TValue> {

	private Hashtable<TKey, TValue> hashtable = new Hashtable<TKey, TValue>();

	public int size() {
		return hashtable.size();
	}

	public List<TValue> all() {
		return new ArrayList<TValue>(hashtable.values());
	}

	public void add(TKey key, TValue value) {
		hashtable.put(key, value);
	}

	public void remove(TKey key) {
		hashtable.remove(key);
	}

	public TValue get(TKey key) {
		return hashtable.get(key);
	}

	public void clear(){
		hashtable.clear();
	}
}
