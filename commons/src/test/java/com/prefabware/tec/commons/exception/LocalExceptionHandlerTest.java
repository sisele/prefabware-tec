package com.prefabware.tec.commons.exception;

import org.junit.*;

/**
 * Created by stefan on 23.08.17.
 */
public class LocalExceptionHandlerTest {
	Stub stub;

	static class Checked extends Exception {
		Checked(String message) {
			super(message);
		}
	}

	static class Stub {
		void void_() throws Checked {
			throw new Checked("void");
		}

		String zeroArgs() throws Checked {
			throw new Checked("0");
		}

		String oneArg(String s1) throws Checked {
			throw new Checked(s1);
		}

		String twoArg(String s2, Integer i2) throws Checked {
			throw new Checked(s2);
		}
	}

	@Before
	public void setUp() throws Exception {
		stub = new Stub();

	}

	@Test
	public void testRethrow() throws Exception {

	}

	@Test
	public void testRethrowing0() throws Exception {
		try {
			LocalExceptionHandler.unchecked(stub::zeroArgs);
			Assert.fail("Exception should be thrown");
		} catch (Exception c) {
		}

	}

	@Test
	public void testRethrowing1() throws Exception {
		String a = "a";
		try {
			LocalExceptionHandler.unchecked(() -> stub.oneArg(a));
			Assert.fail("Exception should be thrown");
		} catch (Exception c) {
		}

	}

	@Test
	public void testRethrowingVoid() throws Exception {
		String a = "a";
		try {
			LocalExceptionHandler.unchecked(() -> stub.void_());
			Assert.fail("Exception should be thrown");
		} catch (Exception c) {
		}

	}


	@Test
	public void testSwallow() throws Exception {

	}
}