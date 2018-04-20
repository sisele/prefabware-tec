package com.prefabware.tec.commons.string;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prefabware.tec.spring.api.RequestTemplate;
import com.prefabware.tec.spring.api.jackson.Json;
import com.prefabware.tec.spring.api.property.JsonProperty;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by stefan on 07.02.17.
 */
public class RequestTemplateTest {
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class Value{
		public String a;
		public int b;

	}

	@Before
	public void before() {
	}


	@Test
	public void testInitializeManually() {
		Json json = Json.of("{'uri':'http://some-endpoint','headers':{'header1':'value1','header2':'value2'}}");
		JsonProperty p = new JsonProperty(json.toString());
		RequestTemplate def = p.value(RequestTemplate.class);
		assertNotNull(def);
		assertEquals(2,def.headers.size());
		assertEquals("value1",def.headers.get("header1").get(0));
		assertEquals("value2",def.headers.get("header2").get(0));


	}
}
