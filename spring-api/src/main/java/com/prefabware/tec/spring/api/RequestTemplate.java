package com.prefabware.tec.spring.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.web.util.UriTemplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * template for a http request
 * normal use case
 * <ul>
 *     <li>uri has no unresolved placeholders</li>
 *     <li>is used for a GET request</li>
 *     <li>headers can be used but are not required</li>
 * </ul>
 * Created by stefan on 07.02.17.
 */
public class RequestTemplate {
	public static class Builder{
		/**
		 * the UNENCODED template for the request
		 */
		UriTemplate uri;
		HttpHeaders headers;
		Class<?> responseType;
		/**
		 * the UNENCODED template for the request
		 */
		public Builder uri(UriTemplate uri){
			this.uri=uri;
			return this;
		}
		/**
		 * the UNENCODED uri string for the request
		 */
		public Builder uri(String uri){
			this.uri=new UriTemplate(uri);
			return this;
		}

		public Builder headers(HttpHeaders headers){
			this.headers=headers;
			return this;
		}
		public Builder responseType(Class<?> responseType){
			this.responseType=responseType;
			return this;
		}
		public RequestTemplate build(){
			return new RequestTemplate(uri,headers,responseType);
		}
	}
	public static Builder create(){
		return new Builder();
	}
	/**
	 * the uri for the request
	 * may contain placeholder
	 */
	public final UriTemplate uri;
	/**
	 * the headers for the request
	 */
	public final HttpHeaders headers;
	/**
	 * the expected response type
	 * if not specified,a Map will be used
	 */
	public final Class<?> responseType;

	public RequestTemplate(UriTemplate uri, HttpHeaders headers, Class<?> responseType) {
		Assert.notNull(uri, "uri must not be null");
		this.uri = uri;
		if (headers != null) {
			this.headers = headers;
		} else {
			this.headers = new HttpHeaders();
		}
		if (responseType != null) {
			this.responseType = responseType;
		} else {
			this.responseType = String.class;
		}
	}

	@JsonCreator
	public RequestTemplate(@JsonProperty("uri") UriTemplate uri, @JsonProperty("headers") HttpSingleHeaders headers, @JsonProperty("responseType")  Class<?> responseType) {
		this(uri, headers.toHeaders(), responseType);
	}

	public URI toUri() {
		try {
			return new URI(uri.toString());
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("uri is invalid", e);
		}
	}

	@Override
	public String toString() {
		return "RequestDef{" +
				"uri=" + uri +
				", headers=" + headers +
				", responseType=" + responseType +
				'}';
	}
	@JsonAnyGetter
	public Map<String,Object> toJson(){
		Map<String, Object> map = new LinkedHashMap<>();
		return map;
	}
}
