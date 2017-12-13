package com.prefabware.tec.commons.exception;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by stefan on 13.12.17.
 */
public class Catch {
	static class Throwables {
		final List<Class<? extends Throwable>> throwables;

		Throwables(Class<? extends Throwable>... toCatch) {
			if (toCatch != null && toCatch.length > 0) {
				this.throwables = Arrays.asList(toCatch);
			} else {
				this.throwables = Collections.emptyList();
			}
		}

		boolean contains(Throwable t) {
			return throwables.contains(t.getClass());
		}
	}
	/**
	 * to call the Callable, catching all Exceptions that are passed in the argument 'toCatch'
	 * if any exception is thrown,and the exception class is contained in toCatch,the exception is caught
	 * and null is returned
	 * @param callable
	 * @param toCatch
	 * @param <T>
	 * @return
	 */
	public static <T> T execute(Callable<T> callable, Class<? extends Throwable>... toCatch) {
		return execute(callable, null, toCatch);
	}

	/**
	 * to call the Callable, catching all Exceptions that are passed in the argument 'toCatch'
	 * if any exception is thrown,and the exception class is contained in toCatch,the exception is caught
	 * and the defaultis returned
	 * @param callable
	 * @param default_
	 * @param toCatch
	 * @param <T>
	 * @return
	 */
	public static <T> T execute(Callable<T> callable, T default_, Class<? extends Throwable>... toCatch) {
		try {
			return callable.call();
		} catch (Throwable t) {
			Throwables throwables = new Throwables(toCatch);
			if (throwables.contains(t)) {
				//need to catch this and return the default
				return default_;
			} else {
				return LocalExceptionHandler.rethrow(t);
			}
		}

	}
}
