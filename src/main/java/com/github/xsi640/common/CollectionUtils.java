package com.github.xsi640.common;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;


/**
 * 集合辅助工具类
 *
 * @author SuYang
 */
public class CollectionUtils {
    /**
     * text是否存在于array集合中
     *
     * @param array      要检测的集合
     * @param text       要检测的文本
     * @param ingoreCase 是否大小写敏感
     * @return true-存在，false-不存在
     */
    public static boolean exists(String[] array, String text, boolean ingoreCase) {
        boolean result = false;
        if (!StringUtils.isEmpty(text) && isEmpty(array)) {
            for (String s : array) {
                if (ingoreCase) {
                    if (s.equals(text)) {
                        result = true;
                        break;
                    }
                } else {
                    if (s.equalsIgnoreCase(text)) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 根据某一个属性的值，在结合中查询匹配的对象，如果查找到了，返回该对象，如果没有，返回null
     *
     * @param list      要查询的集合
     * @param propName  属性名称
     * @param propValue 属性的值
     * @param <T>       集合中对象的类型
     * @return 匹配的对象
     * @throws NoSuchFieldException   没有找到指定的属性
     * @throws IllegalAccessException 指定的属性不能访问
     */
    public static <T> T findOne(Iterable<T> list, String propName, Object propValue) throws NoSuchFieldException, IllegalAccessException {
        if (list == null)
            return null;

        Iterator<T> iterator = list.iterator();
        Field field = null;
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (field == null) {
                field = t.getClass().getDeclaredField(propName);
                field.setAccessible(true);
            }
            Object value = field.get(t);
            if (value == null && propValue == null) {
                return t;
            } else if (value != null && value.equals(propValue) ||
                    propValue != null && propValue.equals(value)) {
                return t;
            }
        }
        return null;
    }


    /**
     * 查找集合中存在的一个项，如果找到返回该对象，没有找到返回null
     *
     * @param list 检测的集合
     * @param func 判断方法
     * @param <T>  集合中对象的类型
     * @return 查找的对象
     */
    public static <T> T findOne(Iterable<T> list, Predicate<T> func) {
        if (list == null)
            return null;

        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (func.test(t)) {
                return t;
            }
        }
        return null;
    }

    /**
     * 查找集合中存在的项（返回所有匹配的项）
     *
     * @param list 检测的集合
     * @param func 判断方法
     * @param <T>  集合中对象的类型
     * @return 匹配的所有的对象
     */
    public static <T> List<T> find(Iterable<T> list, Predicate<T> func) {
        List<T> result = new ArrayList<>();
        if (list == null || func == null)
            return result;

        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (func.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * 数组转List集合
     *
     * @param array 数组
     * @param <T>   数组中对象的类型
     * @return List集合
     */
    public static <T> List<T> toList(T[] array) {
        return Arrays.asList(array);
    }

    /**
     * Iterable转List
     *
     * @param list 集合
     * @param <T>  Iterable中的对象类型
     * @return List集合
     */
    public static <T> List<T> toList(Iterable<T> list) {
        if (list == null)
            return null;
        Iterator<T> iterator = list.iterator();
        List<T> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    /**
     * Iterable转数组
     *
     * @param list  要转换的集合
     * @param array 转换的数组
     * @param <T>   集合中数据的类型
     * @return 转换完成的数组
     */
    public static <T> T[] toArray(Iterable<T> list, T[] array) {
        if (list == null)
            return null;

        List<T> arrayList = toList(list);
        return arrayList.toArray(array);
    }

    /**
     * Iterable转数组
     *
     * @param list  要转换的集合
     * @param clazz 集合中数据的类型
     * @param <T>   集合中数据的类型
     * @return 转换完成的数组
     */
    public static <T> T[] toArray(Iterable<T> list, Class<T> clazz) {
        if (list == null)
            return null;

        Iterator<T> it = list.iterator();
        int size = 0;
        while (it.hasNext()) {
            it.next();
            size++;
        }

        T[] result = (T[]) Array.newInstance(clazz, size);
        if (size == 0)
            return result;
        it = list.iterator();
        size = 0;
        while (it.hasNext()) {
            result[size] = it.next();
            size++;
        }
        return result;
    }

    /**
     * list集合转数组
     *
     * @param list  要转换的集合
     * @param array 转换的数组
     * @param <T>   集合中的类型
     * @return 转换完成的数组
     */
    public static <T> T[] toArray(List<T> list, T[] array) {
        if (list == null)
            return null;

        return list.toArray(array);
    }

    /**
     * list集合转数组
     *
     * @param list  要转换的集合
     * @param clazz 集合中的类型
     * @param <T>   集合中的类型
     * @return 转换完成的数组
     */
    public static <T> T[] toArray(List<T> list, Class<T> clazz) {
        if (list == null)
            return null;

        T[] result = (T[]) Array.newInstance(clazz, list.size());
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * 去除List中重复的对象
     *
     * @param list 要去除重复的集合
     * @param <T>  集合中对象的类型
     * @return 去除重复对象后的集合
     */
    public static <T> List<T> distinct(Iterable<T> list) {
        if (list == null)
            return null;

        Set<T> set = new HashSet<>();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            set.add(iterator.next());
        }
        List<T> result = new ArrayList<>(set.size());
        result.addAll(set);
        return result;
    }

    /**
     * 根据字符串、分隔符、返回字符串集合
     *
     * @param text     字符串
     * @param strSplit 分隔符
     * @return 字符串集合
     */
    public static List<String> split(String text, String strSplit) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(strSplit))
            return result;

        String[] array = text.split(strSplit);
        if (array != null && array.length > 0) {
            for (String s : array) {
                if (s == null)
                    continue;
                result.add(s.trim());
            }
        }
        return result;
    }

    /**
     * 返回集合中，某个属性的集合
     *
     * @param list        要查询的集合
     * @param propName    属性名称
     * @param <TProperty> 属性的类型
     * @param <TObject>   集合中对象的类型
     * @return 返回的指定属性的对象集合
     * @throws NoSuchFieldException 没有找到指定的属性
     */
    public static <TProperty, TObject> List<TProperty> getPropertyList(Iterable<TObject> list, String propName) throws NoSuchFieldException {
        List<TProperty> result = new ArrayList<TProperty>();
        if (list == null || StringUtils.isEmpty(propName))
            return result;

        Iterator<TObject> iterator = list.iterator();
        Field field = null;
        while (iterator.hasNext()) {
            TObject t = iterator.next();
            if (field == null) {
                field = t.getClass().getDeclaredField(propName);
            }
            field.setAccessible(true);
            try {
                @SuppressWarnings("unchecked")
                TProperty value = (TProperty) field.get(t);
                result.add(value);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 将指定的集合转换成另一个集合
     *
     * @param list      检测的集合
     * @param converter 转换方法
     * @param <Tin>     集合中对象的类型
     * @param <Tout>    要转换的类型
     * @return 转换后的集合
     */
    public static <Tin, Tout> List<Tout> convertAll(Iterable<Tin> list, Function<Tin, Tout> converter) {
        List<Tout> result = new ArrayList<>();
        if (list == null || converter == null)
            return result;

        Iterator<Tin> iterator = list.iterator();
        while (iterator.hasNext()) {
            Tin t = iterator.next();
            Tout out = converter.apply(t);
            result.add(out);
        }
        return result;
    }

    /**
     * 将集合转换成按指定字符串分割的字符串
     *
     * @param list     要转换的集合
     * @param strSplit 分隔符
     * @param <T>      集合中对象的集合
     * @return 转换后的字符串
     */
    public static <T> String toString(Iterable<T> list, String strSplit) {
        return toString(list, strSplit, null);
    }


    /**
     * 将集合转换成按指定字符串分割的字符串
     *
     * @param list         集合
     * @param strSplit     分隔符
     * @param toStringFunc 对象转字符串的方法，如果为空直接用toString方法
     * @param <T>          集合中对象的类型
     * @return 转换完成的字符串
     */
    public static <T> String toString(Iterable<T> list, String strSplit, Function<T, String> toStringFunc) {
        if (list == null)
            return "";

        StringBuilder sb = new StringBuilder();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (toStringFunc != null) {
                sb.append(toStringFunc.apply(t));
            } else {
                sb.append(t.toString());
            }
            if (iterator.hasNext()) {
                sb.append(strSplit);
            }
        }
        return sb.toString();
    }

    /**
     * 取出集合中的指定对象
     *
     * @param list 检测的集合
     * @param skip 跳过几个
     * @param take 取出几个
     * @param <T>  集合中对象的类型
     * @return 取出的集合
     */
    public static <T> List<T> take(Collection<T> list, int skip, int take) {
        List<T> result = new ArrayList<T>();
        if (skip < list.size() && take > 0) {
            int index = 0;
            Iterator<T> itr = list.iterator();
            while (itr.hasNext()) {
                T t = itr.next();
                if (index >= skip && index < skip + take) {
                    result.add(t);
                }
                index++;
            }
        }
        return result;
    }

    /**
     * 检测数组是否为空
     *
     * @param arr 要检测的数组
     * @return true-为空，false-不为空
     */
    public static boolean isEmpty(Object[] arr) {
        return arr != null && arr.length > 0;
    }
}
