package com.pawpaw.framework.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CollectionConventer {

	public static final Logger logger = LoggerFactory.getLogger(com.pawpaw.common.util.CollectionConventer.class);

	public static int[] collection2IntArray(Collection<Integer> coll) {
		if (coll == null) {
			return new int[0];
		}
		int[] r = new int[coll.size()];
		Iterator<Integer> it = coll.iterator();
		int i = 0;
		while (it.hasNext()) {
			Integer v = it.next();
			r[i] = v;
			i++;
		}
		return r;
	}

	public static List<Integer> collection2IntList(Collection<String> coll) {

		List<Integer> r = new LinkedList<Integer>();
		if (coll == null) {
			return r;
		}
		for (String s : coll) {
			try {
				r.add(Integer.parseInt(s));
			} catch (NumberFormatException e) {
				logger.warn("skip this element,not a number:{}", s);
			}

		}
		return r;

	}

	public static String[] collection2StringArray(Collection<? extends Object> coll) {
		if (coll == null) {
			return new String[0];
		}
		String[] r = new String[coll.size()];
		Iterator<? extends Object> it = coll.iterator();
		int i = 0;
		while (it.hasNext()) {
			Object v = it.next();
			r[i] = v.toString();
			i++;
		}
		return r;
	}

	public static <T> List<T> array2List(T[] arr) {
		List<T> r = new LinkedList<>();
		if (arr == null) {
			return r;
		}
		for (T s : arr) {
			r.add(s);
		}
		return r;
	}

	public static List<Integer> array2List(int[] arr) {
		List<Integer> r = new LinkedList<>();
		if (arr == null) {
			return r;
		}
		for (int s : arr) {
			r.add(s);
		}
		return r;
	}

	public static Set<Integer> array2Set(int[] arr) {
		Set<Integer> r = new HashSet<>();
		if (arr == null) {
			return r;
		}
		for (Integer s : arr) {
			r.add(s);
		}
		return r;
	}

	/**
	 * 先把目标变成set，然后再切分
	 * 
	 * @param source
	 * @param size
	 * @return
	 */
	public static <T> List<List<T>> devideSet(Collection<T> source, int size) {
		Set<T> s = new HashSet<T>();
		if (source != null) {
			s.addAll(source);
		}
		return devide(s, size);
	}

	public static <T> List<List<T>> devide(Collection<T> source, int size) {
		List<List<T>> r = new LinkedList<List<T>>();
		if (source == null || source.size() == 0) {
			return r;
		}
		Iterator<T> it = source.iterator();
		List<T> tmpList = new LinkedList<>();
		while (it.hasNext()) {
			T t = it.next();
			tmpList.add(t);
			if (tmpList.size() >= size) {
				r.add(tmpList);
				tmpList = new LinkedList<>();
			}
		}
		if (tmpList.size() > 0) {
			r.add(tmpList);
		}
		return r;
	}

}
