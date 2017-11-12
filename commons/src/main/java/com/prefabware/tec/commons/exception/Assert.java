package com.prefabware.tec.commons.exception;

/**
 * Created by stefan on 12.11.17.
 */
public abstract class Assert {
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void fail(String message) {
		throw new IllegalArgumentException(message);
	}
}
