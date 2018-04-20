package com.prefabware.tec.commons.spring.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by stefan on 14.10.16.
 */
public class TemplateTest {
	@Test
	public void testExpandOne() {
		Template t = new Template("some text with <placeholder>");
		String replaced = t.expand("placeholder", "replaced text");
		assertEquals("some text with replaced text", replaced.toString());
	}

	@Test
	public void testExpandTwo() {
		Template t = new Template("some text with <placeholder1> and <placeholder2>");
		Map<String,String> map=new HashMap<>();
		map.put("placeholder1","value1");
		map.put("placeholder2","value2");
		String expanded = t.expand(map);
		assertEquals("some text with value1 and value2", expanded);
	}
	
	
	@Test
	public void testExpandNull() {
		String expanded=null;
		Template t = new Template("some text with <placeholder1> and <placeholder2>");
		Map<String,String> map=new HashMap<>();
		map.put("placeholder1",null);
		map.put("placeholder2",null);
		try{
		 expanded = t.expand(map);
		}catch(IllegalArgumentException exe){
			exe.getMessage();
			assertTrue (exe.getMessage().contains("values for placeholders must not be null but found "));
		}
	}
		
		

	@Test
	public void testCustomPreSufix() {
		Template t = new Template("some text with [placeholder1] and [placeholder2]","[","]");
		Map<String,String> map=new HashMap<>();
		map.put("placeholder1","value1");
		map.put("placeholder2","value2");
		String expanded = t.expand(map);
		assertEquals("some text with value1 and value2", expanded);
	}
	
	@Test
	public void testEquals(){
		Template t1 = new Template("One");
		Template t2 = new Template("Two");
		boolean flag=t1.equals(t2);
		assertFalse(flag);
		boolean flag2=t1.equals(new Template("One"));
		assertTrue(flag2);
		
	}



	@Test
	public void testRegex() {
		Pattern p=Pattern.compile("\\$\\{(.*?)\\}");
		Matcher matcher = p.matcher("n1 ${v1} n2 ${v2}");
		assertTrue(matcher.find());
		assertEquals("v1",matcher.group(1));
		assertTrue(matcher.find());
		assertEquals("v2",matcher.group(1));
	}
	@Test
	public void testRegexLoop() {
		Pattern p=Pattern.compile("\\$\\{(.*?)\\}");
		Matcher matcher = p.matcher("n1 ${v1} n2 ${v2}");

		List all=new ArrayList<String>();
		while(matcher.find()){
		all.add(matcher.group(1));
		}

		assertEquals(2,all.size());
		assertEquals("v1",all.get(0));
		assertEquals("v2",all.get(1));
	}
}
