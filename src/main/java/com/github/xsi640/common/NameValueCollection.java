package com.github.xsi640.common;

import java.util.*;

/**
 * 减值的集合，主要用于描述http请求的参数
 */
public class NameValueCollection {

	private Map<String, List<String>> map = Collections.synchronizedMap(new HashMap<>());

	/**
	 * 获得集合大小
	 * @return 集合大小
	 */
	public int size() {
		return map.size();
	}

	/**
	 * 集合是否为空
	 * @return true-集合为空，false-集合不为空
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/**
	 * 集合是否存在某个Key
	 * @param key 集合Key
	 * @return true-存在，false-不存在
	 */
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	/**
	 * 集合是否存在某个值
	 * @param value 集合中的值
	 * @return true-存在，false-不存在
	 */
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	/**
	 * 获取某个key的值
	 * @param key 集合Key
	 * @return 获取集合中的值
	 */
	public String get(Object key) {
		List<String> lists = map.get(key);
		if (lists == null || lists.size() == 0)
			return "";
		return CollectionUtils.toString(lists, ",");
	}

	/**
	 * 设置key-value
	 * @param key 集合Key
	 * @param value 集合中的值
	 */
	public void put(String key, String value) {
		List<String> lists = map.get(key);
		if (lists == null) {
			lists = new ArrayList<String>();
			lists.add(value);
		} else {
			lists.add(value);
		}
		this.map.put(key, lists);
	}

	/**
	 * 移除
	 * @param key 集合Key
	 */
	public void remove(Object key) {
		map.remove(key);
	}

	/**
	 * 清空集合
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * 获取所有Key
	 * @return 所以的Key集合
	 */
	public Set<String> keySet() {
		return map.keySet();
	}

	/**
	 * 获取所有值
	 * @return 所有值的集合
	 */
	public Collection<List<String>> values(){
		return map.values();
	}

	/**
	 * 获取所有值（字符串形式）
	 * @return 所有值的集合（字符串）
	 */
	public Collection<String> valuesAsString() {
		Collection<List<String>> collection = map.values();
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
