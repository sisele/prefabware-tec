package com.prefabware.tec.commons.stream;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by stefan on 20.12.16.
 */
public class StreamUtils {


	/**
	 * to create a Stream for an Iterator
	 * @param fields
	 * @param <T>
	 * @return
	 */
	public static <T> Stream<T> of(Iterator<T> fields) {
		if (fields == null) {
			return Stream.empty();
		}
		Iterable<T> iterable = () -> fields;
		return StreamSupport.stream(iterable.spliterator(), false);
	}
}
