package com.prefabware.tec.commons.nulls;

import java.util.*;
import java.util.stream.Stream;

/**
 * to provide null safe access
 * Created by stefan on 28.03.17.
 */
public class Nullsafe {
	public static boolean isEmpty(Collection collection) {
		return collection == null || collection.isEmpty();
	}

	public static <T> List<T> list(List<T> list) {
		if (list == null) {
			return new ArrayList<>();
		} else {
			return list;
		}
	}

	/**
	 * to get the first element of a List that may be null or empty
	 *
	 * @param provided
	 * @param <T>
	 * @return
	 */
	public static <T> Optional<T> first(List<T> provided) {
		List<T> list = Nullsafe.list(provided);
		if (list==null||list.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(list.get(0));
		}
	}


	/**
	 * to allow null safe access to a stream with the elements of the collection
	 * CAUTION : if you
	 *
	 * @return a stream with the elements of the given collection
	 */
	public static <T> Stream<T> stream(Collection<T> collection) {
		if (collection == null) {
			return Stream.empty();
		} else {
			return collection.stream().filter(e -> e != null);
		}
	}

	/**
	 * TODO all other methods should return streams also, instead of collections
	 *
	 * @param array
	 * @param <T>
	 * @return
	 */
	public static <T> Stream<T> stream(T... array) {
		if (array == null || array.length == 0) {
			return (Stream<T>) Collections.emptyList().stream();
		} else {
			Collection<T> ts = Arrays.asList(array);
			return stream(ts);
		}
	}

	/**
	 * to convert a Boolean nullsafe into a boolean primitive
	 * Boolean can be null, which leads to NullpointerExceptions, when tried to autobox it into a boolean primitive
	 * This method treats null as false, as required for most use cases
	 *
	 * @param value
	 * @return
	 */
	public static boolean booleanValue(Boolean value) {
		if (value == null) {
			return false;
		} else {
			return value.booleanValue();
		}

	}

	public static boolean equals(Object a, Object b) {
		return Objects.equals(a, b);
	}

	public static Boolean get(Boolean value) {
		return booleanValue(value);
	}

	public static String get(String value) {
		if (value == null) {
			return "";
		}else{return value;}

	}

	public static <T>List<T> list(T ...values) {
		if(values==null){
			return new ArrayList<>();
		}
		return Arrays.asList(values);
	}
}
