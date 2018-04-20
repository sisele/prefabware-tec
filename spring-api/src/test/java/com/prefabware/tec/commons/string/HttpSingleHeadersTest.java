package com.prefabware.tec.commons.string;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.prefabware.tec.spring.api.HttpSingleHeaders;
import com.prefabware.tec.spring.api.jackson.Json;
import org.junit.*;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static org.junit.Assert.*;

/**
 * Created by stefan on 14.07.16.
 */
public class HttpSingleHeadersTest {
	@JsonInclude(NON_EMPTY)
	@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
	static class Holder {
		HttpSingleHeaders map = new HttpSingleHeaders();
	}

	@Before
	public void before() {
	}	

	@Test
	public void deserializeSingleValues() {
		Json json = Json.of("{'map':{'header1':'value1','header2':'value2'}}");
		Holder h = json.toObject(Holder.class);
		assertNotNull(h.map);
		assertEquals(2,h.map.size());
		assertFalse(h.map.containsMulti());
		assertEquals("value1",h.map.getFirst("header1"));
		assertEquals("value2",h.map.getFirst("header2"));
	}

	@Test
	public void serializeSingleValues() throws Exception {
		Holder h = new Holder();
		h.map.add("key1","value1");
		h.map.add("key2","value2");
		Json json = Json.of(h);
		assertEquals("map should contain key1 and key2",Json.of("{'map':{'key1':'value1','key2':'value2'}}"),json);
	}

	@Test
	public void serializeMultiValues() throws Exception {
		Holder h = new Holder();
		h.map.add("key1","value1.1");
		h.map.add("key1","value1.2");
		Json json = Json.of(h);
		assertEquals("map should contain only the first value added for key1",Json.of("{'map':{'key1':'value1.1'}}"),json);
	}

	@Test
	public void deserializeMultiValues() throws Exception {
		Json json = Json.of("{'map':{'key1':['value1.1','value1.2']}}");

		Holder h = json.toObject(Holder.class);
		assertTrue(h.map.containsMulti());
		assertEquals("map should contain only the first value added for key1",Json.of("{'map':{'key1':'value1.1'}}"),Json.of(h));
	}
}
