package com.prefabware.tec.commons.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.*;

import static org.junit.Assert.*;

public class StreamUtilsTest {
	@Test
	public void testOf(){
		List<String> strList = new ArrayList<String>();
		strList.add("one");
		strList.add("two");
		strList.add("three");
		strList.add("");
		
		Predicate<String> isEmpty = String::isEmpty;
		Predicate<String> notEmpty = isEmpty.negate();
		
		Stream<String> stringStream=StreamUtils.of(strList.iterator());
		
		List<String> finalList=(stringStream.filter(notEmpty).collect(Collectors.toList()));
		assertTrue(finalList.contains("one"));
		assertFalse(finalList.contains(""));
	}

}
