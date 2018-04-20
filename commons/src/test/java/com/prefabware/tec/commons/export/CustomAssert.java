package com.prefabware.tec.commons.export;

import static com.prefabware.tec.commons.exception.Assert.fail;
import static org.junit.Assert.*;

/**
 * Created by stefan on 28.12.17.
 */
public class CustomAssert {
	/**
	 * to test,that the runnable fails throwing an exception of type throwableClass
	 * @param runnable
	 * @param throwableClass
	 * @return the thrown Throwable, to allow to checkfor the correct message
	 */
	public static Throwable assertThrows(Runnable runnable, Class<? extends Throwable>throwableClass){
		try {
			runnable.run();
			fail("Expected exception of type "+ throwableClass);
		}catch (Throwable t){
			assertEquals(throwableClass,t.getClass());
			return t;
		}
		return null;
	}
}
