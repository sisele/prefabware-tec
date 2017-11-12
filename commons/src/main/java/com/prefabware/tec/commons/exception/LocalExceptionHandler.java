package com.prefabware.tec.commons.exception;

import java.util.function.Predicate;

import com.prefabware.tec.commons.function.RunnableThrowing;
import com.prefabware.tec.commons.function.SupplierThrowing;


/**
 * to swallow exceptions
 * In some, rare cases we need to swallow exceptions.
 * <p>
 * Using this class to handle this in a single location
 * this allows different handling e.g in tests
 * <p>
 * If this class needs more configuration, we should make it a spring bean without static methods
 * Created by stefan on 04.08.17.
 */
public class LocalExceptionHandler {

	/**
	 * to rethrow the exception, e.g. in tests
	 * to avoid that exceptions are swallowed, that should make the test fail
	 */
	public static Predicate<Throwable> rethrow = t -> false;

	public static <T> T rethrow(Throwable throwable) {
		if (throwable instanceof RuntimeException) {
			//do not wrap if not neccesary
			throw ((RuntimeException) throwable);
		} else {
			throw new RuntimeException(throwable);
		}
	}

	public static <T> T unchecked(SupplierThrowing<T> function) {
		try {
			return function.get();
		} catch (Throwable throwable) {
			return rethrow(throwable);

		}
	}

	public static void unchecked(RunnableThrowing function) {
		try {
			function.run();
		} catch (Throwable throwable) {
			rethrow(throwable);

		}
	}

	/**
	 * to swallow the exception
	 *
	 * @param throwable
	 * @return - always returns null, this allows to write
	 * <pre>return Swallow.exception(e);</pre>
	 * instead of an additional
	 * <pre>return null;</pre>
	 */
	public static <T> T swallow(Throwable throwable, String reason) {
		Assert.notNull(reason, "you must provide a reason");
		//default handling is log as info, if it was a real error, we must not swallow it
		//LOGGER.info("intentionally swallowing exception. Reason = {}, exception = {} ", reason, throwable);
		if (rethrow.test(throwable)) {
			rethrow(throwable);
		}
		return null;
	}
}
