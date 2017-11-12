package com.prefabware.tec.commons.uri;

import java.net.URI;

import org.junit.*;

import static org.junit.Assert.*;

public class UriTest {


	@Test
	public void uriOnly() throws Exception {
		URI uri = new URI("https", null, "example.com", -1, null, "space=a b&equal==&plus=+", null);
		assertEquals("https://example.com?space=a%20b&equal==&plus=+", uri.toString());
		assertEquals("space=a b&equal==&plus=+", uri.getQuery());

		URI uriFromString = URI.create(uri.toString());

		assertEquals(uri, uriFromString);
	}

	@Test
	public void uriEqualEncoded() throws Exception {
		URI uri = URI.create("https://example.com?equal=a%3Db");
		assertEquals("equal=a=b", uri.getQuery());
	}

	@Test
	public void testKeyValue() throws Exception {
		UriModifier.QueryParm queryParm = new UriModifier.QueryParm("space=a b");
		assertEquals("space", queryParm.key);
		assertEquals("a b", queryParm.value);
	}

	@Test
	public void emptyQuery() throws Exception {
		UriModifier.Query query=new UriModifier.Query();

		assertEquals("", query.toString());
	}


	@Test
	public void uriB() throws Exception {
		URI uri = new URI("https", null, "example.com", -1, null, "space=a b&equal==&plus=+", null);
		assertEquals("https://example.com?space=a%20b&equal==&plus=+", uri.toString());
		assertEquals("space=a b&equal==&plus=+", uri.getQuery());

		UriModifier b = new UriModifier(uri);
		UriModifier.Query query = b.query();
		assertEquals(3, query.size());
		assertEquals("space", query.get(0).key);
		assertEquals("a b", query.get(0).value);
		assertEquals("equal", query.get(1).key);
		assertEquals("=", query.get(1).value);
		assertEquals("plus", query.get(2).key);
		assertEquals("+", query.get(2).value);
	}

	@Test
	public void uriChange() throws Exception {
		URI uri = URI.create("https://example.com?a=1&b=2");
		URI c = UriModifier.create(uri)
				.addQuery("space", "3 4")
				.addQuery("plus", "5+6")
				.addQuery("equal", "6=7")
				.uri();
		assertEquals("https://example.com?a=1&b=2&space=3%204&plus=5+6&equal=6=7",c.toString());
		;
	}

	@Test
	public void fromEmpty() throws Exception {
		URI c = UriModifier.create()
				.schema("https")
				.host("example.com")
				.clearQuery()
				.addQuery("space", "3 4")
				.addQuery("plus", "5+6")
				.addQuery("equal", "6=7")
				.uri();
		assertEquals("https://example.com?space=3%204&plus=5+6&equal=6=7",c.toString());
	}

	@Test
	public void spaceInPath() throws Exception {
		URI c = UriModifier.create()
				.schema("https")
				.host("example.com")
				.path("/pa th")
				.uri();
		assertEquals("https://example.com/pa%20th",c.toString());
	}

	@Test
	@Ignore
	public void forcePlusEncoded() throws Exception {
		URI c = UriModifier.create()
				.schema("https")
				.host("example.com")
				.addQuery("plus", "5%3D6")
				.uri();
		assertEquals("https://example.com?space=3%204&plus=5+6&equal=6=7",c.toString());
	}


}