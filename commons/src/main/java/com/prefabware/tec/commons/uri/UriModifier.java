package com.prefabware.tec.commons.uri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by stefan on 12.11.17.
 */
public class UriModifier {

	String schema;
	String userInfo;
	String host;
	int port = -1;
	String path;
	Query query;
	String fragment;

	static UriModifier create(URI uri) {
		return new UriModifier(uri);
	}

	static UriModifier create() {
		return new UriModifier();
	}

	URI uri() {
		try {
			String query = query().toString();
			return new URI(schema, userInfo, host, port, path, query.isEmpty() ? null : query, fragment);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public String fragment() {
		return fragment;
	}

	public String host() {
		return host;
	}

	public String path() {
		return path;
	}

	public int port() {
		return port;
	}

	public String schema() {
		return schema;
	}

	public String userInfo() {
		return userInfo;
	}

	public UriModifier fragment(String fragment) {
		this.fragment = fragment;
		return this;
	}

	public UriModifier host(String host) {
		this.host = host;
		return this;
	}

	public UriModifier path(String path) {
		this.path = path;
		return this;
	}

	public UriModifier port(int port) {
		this.port = port;
		return this;
	}

	public UriModifier schema(String schema) {
		this.schema = schema;
		return this;
	}

	public UriModifier userInfo(String userInfo) {
		this.userInfo = userInfo;
		return this;
	}

	UriModifier clearQuery() {
		this.query.clear();
		return this;
	}

	public static class Query {
		public Query(List<QueryParm> list) {
			this.list = list;
		}

		List<QueryParm> list = new ArrayList<>();

		public Query() {

		}

		public boolean add(QueryParm queryParm) {
			return list.add(queryParm);
		}

		public void clear() {
			list.clear();
		}

		public boolean addAll(Collection<? extends QueryParm> c) {
			return list.addAll(c);
		}

		public boolean isEmpty() {
			return list.isEmpty();
		}

		public int size() {
			return list.size();
		}

		public Stream<QueryParm> stream() {
			return list.stream();
		}

		public QueryParm get(int index) {
			return list.get(index);
		}

		void add(String key, String value) {
			add(new QueryParm(key, value));
		}

		@Override
		public String toString() {
			return stream().map(p -> p.toString())
					.collect(Collectors.joining("&"));
		}
	}

	public static class QueryParm {
		/**
		 * to split key and value at '=', while not splitting at '=' in the value
		 */
		Pattern pattern = Pattern.compile("([^=]*)=(.*)");
		public final String key;
		public final String value;

		QueryParm(String keyValue) {
			if (!keyValue.contains("=")) {
				//key only
				this.key = keyValue;
				this.value = null;
			} else {
				Matcher matcher = pattern.matcher(keyValue);
				if (matcher.matches()) {
					this.key = matcher.group(1);
					this.value = matcher.group(2);
				} else {
					throw new IllegalArgumentException(keyValue + " is not a valid query parameter");
				}
			}
		}

		public QueryParm(String key, String value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
	}

	public UriModifier() {
		this.uri = null;
		this.query = createQuery(null);
	}

	public UriModifier(URI uri) {
		this.uri = uri;
		this.schema = uri.getScheme();
		this.userInfo = uri.getUserInfo();
		this.host = uri.getHost();
		this.port = uri.getPort();
		this.path = uri.getPath();
		this.query = createQuery(uri);
		this.fragment = uri.getFragment();
	}

	final URI uri;

	/**
	 * @return a List containing the unencoded key/valu pairs of the query
	 */
	public Query query() {
		return this.query;
	}

	Query createQuery(URI uri) {
		if (uri == null) {
			return new Query();
		}
		String query = uri.getQuery();
		if (query == null) {
			return new Query();
		}
		String[] split = query
				.split("&");
		return new Query(Arrays.stream(split)
				.map(kv -> new QueryParm(kv))
				.collect(Collectors.toList()));
	}

	public UriModifier addQuery(String key, String value) {
		this.query()
				.add(key, value);
		return this;
	}

}
