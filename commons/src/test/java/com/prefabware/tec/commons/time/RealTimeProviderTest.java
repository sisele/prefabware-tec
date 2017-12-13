package com.prefabware.tec.commons.time;

import org.junit.*;


public class RealTimeProviderTest {

	RealTimeProvider provider = new RealTimeProvider();
	
	/**
	 * To test {@link RealTimeProvider#now()}
	 */
	@Test
	public void test() {
		long t1 = provider.now().toEpochMilli();
		long t2 = System.currentTimeMillis();
		// It is sometime possible that t1 and t2 are not equal, hence considering 10 ms delay.
		Assert.assertTrue(t2 - t1 < 10);
	}

}
