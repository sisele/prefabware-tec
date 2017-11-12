package com.prefabware.tec.commons.nulls;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by stefan on 24.04.17.
 */
public class NullsafeTest {

	@Test
	public void testBoolean() throws Exception {
		assertTrue(Nullsafe.booleanValue(Boolean.TRUE));
		assertFalse(Nullsafe.booleanValue(Boolean.FALSE));
		assertFalse(Nullsafe.booleanValue(null));
	}
}