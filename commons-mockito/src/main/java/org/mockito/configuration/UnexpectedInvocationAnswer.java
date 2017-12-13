package org.mockito.configuration;

import java.util.Arrays;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * to enable strict mocking
 * every unexpected call to a mock is answered with this exception
 * Created by stefan on 01.02.17.
 */
public class UnexpectedInvocationAnswer<T> implements Answer<T> {
	@Override
	public T answer(InvocationOnMock invocation) throws Throwable {
		throw new IllegalStateException("unexpected call to mock "+invocation.getMethod()+" args "+ Arrays.asList(invocation.getArguments()));
	}

}
