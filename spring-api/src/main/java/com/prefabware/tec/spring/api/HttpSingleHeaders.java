package com.prefabware.tec.spring.api;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedCaseInsensitiveMap;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * to handle headers with a single value.
 * {@link org.springframework.http.HttpHeaders} requires a MultiMap and
 * thus is difficult to handle.
 * This class converts between both and can be deserialized from a simple Json hash
 * Created by stefan on 07.02.17.
 */
@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = NONE, setterVisibility = NONE)
public class HttpSingleHeaders extends SingleValueMap{

	/**
	 * Http headers are case insensitive, so the
	 * HttpHeaders is based on a {@link LinkedCaseInsensitiveMap}
	 * which implements case insensitive handling of keys, see there
	 * @return
	 */
	public HttpHeaders toHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.putAll(toMultiValueMap());
		return headers;
	}

	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static HttpSingleHeaders deserialize(Map<String, Object> map) {
		HttpSingleHeaders singles = new HttpSingleHeaders();
		if (map == null || map.isEmpty()) {
			return singles;
		}

		map.entrySet().stream().forEach(e -> singles.add(e.getKey(), e.getValue()));
		return singles;
	}
}
