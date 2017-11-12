package com.prefabware.tec.commons.exception;

import java.util.stream.Stream;

import com.prefabware.tec.commons.stream.StreamUtils;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by stefan on 26.06.17.
 */
public class CauseIteratorUnitTest {

	Exception t1;
	Exception t2;
	Exception t3;

	@Before
	public void setUp() throws Exception {
       t1=new IllegalArgumentException("1");
       t2=new RuntimeException("2",t1);
       t3=new IllegalStateException("3",t2);
	}

	@Test
	public void testCount() throws Exception {
		assertCount(1L, iterator(this.t1));
		assertCount(2L, iterator(this.t2));
		assertCount(3L, iterator(this.t3));
	}

	@Test
	public void testInstanceOf() throws Exception {
		assertTrue(iterator(t3).instanceOf(IllegalArgumentException.class));
		assertTrue(iterator(t3).instanceOf(RuntimeException.class));
		assertTrue(iterator(t3).instanceOf(IllegalStateException.class));
		assertFalse(iterator(t3).instanceOf(NullPointerException.class));

	}

	void assertCount(long expected, CauseIterator iterator) {
		assertEquals(expected, stream(iterator).count());
	}

	private Stream<Throwable> stream(CauseIterator iterator) {
		return StreamUtils.of(iterator);
	}

	CauseIterator iterator(Exception exception) {
		return new CauseIterator(exception);
	}
}