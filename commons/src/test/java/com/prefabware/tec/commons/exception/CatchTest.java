package com.prefabware.tec.commons.exception;

import java.util.concurrent.Callable;

import org.junit.*;

import static com.prefabware.tec.commons.exception.Assert.fail;
import static org.junit.Assert.*;

/**
 * Created by stefan on 13.12.17.
 */
public class CatchTest {
	Callable<Integer> throwingNullPointer;

	@Before
	public void setUp() throws Exception {

		String x = null;
		throwingNullPointer = ()->x.length();
	}

	@Test
	public void testThrowing() throws Exception {

		try {
			Catch.execute(throwingNullPointer,null);
			fail("exception expected");
		}catch (NullPointerException e){
		}
	}
	@Test
	public void testCatching() throws Exception {

		Integer result = Catch.execute(throwingNullPointer,5678, NullPointerException.class);
		assertEquals(new Integer(5678),result);
	}
	@Test
	public void testCatching2nd() throws Exception {

		Integer result = Catch.execute(throwingNullPointer,5678, IllegalArgumentException.class,NullPointerException.class);
		assertEquals(new Integer(5678),result);
	}
}