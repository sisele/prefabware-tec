package com.prefabware.tec.commons.exception;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.prefabware.tec.commons.stream.StreamUtils;


/**
 * to iterate over an exception and its causes
 * Created by stefan on 26.06.17.
 */
public class CauseIterator implements Iterator<Throwable> {

	public static Stream<Throwable> stream(Exception exception) {
		return of(exception).stream();
	}

	public static CauseIterator of(Throwable exception) {
		return new CauseIterator(exception);
	}


	CauseIterator(Throwable root) {
		Assert.notNull(root, "root must not be null");
		this.root = root;
		this.next = root;
	}

	public Stream<Throwable> stream() {
		return StreamUtils.of(this);
	}

	public boolean matches(Predicate<? super Throwable> predicate) {
		return stream()
				.anyMatch(predicate);
	}

	/**
	 *
	 * @param type
	 * @return true if the root exception or one of its causes is an instanceof the given type
	 */
	public boolean instanceOf(Class<? extends Throwable> type) {
		return stream()
				.anyMatch(t->type.isAssignableFrom(t.getClass()));
	}

	final Throwable root;
	Throwable next;

	@Override
	public boolean hasNext() {
		return next != null;
	}

	@Override
	public Throwable next() {
		Throwable current = next;
		Throwable cause = current.getCause();
		if (cause != null && cause != current) {
			next = cause;
		} else {
			next = null;
		}
		return current;
	}
}
