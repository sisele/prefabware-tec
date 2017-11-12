package com.prefabware.tec.commons.stream;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MoreCollectors {
	/**
	 * to collect into a stream while keeping the original order
	 * @param keyMapper
	 * @param valueMapper
	 * @param <T>
	 * @param <K>
	 * @param <U>
	 * @return
	 */
	public static <T, K, U> Collector<T, ?, Map<K, U>> toLinkedMap(
			Function<? super T, ? extends K> keyMapper,
			Function<? super T, ? extends U> valueMapper) {
		return Collectors.toMap(keyMapper, valueMapper,
				(u, v) -> {
					throw new IllegalStateException(String.format("Duplicate key %s", u));
				},
				LinkedHashMap::new);
	}
}