package com.poreader.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class NameValueCollection {

	private Hashtable<String, List<String>> hashtable = new Hashtable<String, List<String>>();

	public int size() {
		return hashtable.size();
	}

	public boolean isEmpty() {
		return hashtable.isEmpty();
	}

	public boolean containsKey(Object key) {
		return hashtable.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return hashtable.containsValue(value);
	}

	public String get(Object key) {
		List<String> lists = hashtable.get(key);
		if (lists == null || lists.size() == 0)
			return "";
		return CollectionUtils.toString(lists, ",");
	}

	public void put(String key, String value) {
		List<String> lists = hashtable.get(key);
		if (lists == null) {
			lists = new ArrayList<String>();
			lists.add(value);
		} else {
			lists.add(value);
		}
		this.hashtable.put(key, lists);
	}

	public void remove(Object key) {
		hashtable.remove(key);
	}

	public void clear() {
		hashtable.clear();
	}

	public Set<String> keySet() {
		return hashtable.keySet();
	}

	public Collection<String> values() {
		Collection<List<String>> collection = hashtable.values();
		if (collection != null && collection.size() > 0) {
			Collection<String> c = new ArrayList<String>(collection.size());
			for (List<String> lists : collection) {
				c.add(CollectionUtils.toString(lists, ","));
			}
			return c;
		}
		return null;
	}
}
