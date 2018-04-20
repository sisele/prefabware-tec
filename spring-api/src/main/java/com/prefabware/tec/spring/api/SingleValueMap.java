package com.prefabware.tec.spring.api;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * {@link MultiValueMap<String, String>} are difficult to handle
 * and often only one value per key is used
 * this class helps to convert between {@link MultiValueMap<String, String>}
 * and normal,single value maps {@link Map}
 * <p>
 * This class converts between both and can be deserialized from a simple Json hash
 * Created by stefan on 07.02.17.
 */
@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = NONE, setterVisibility = NONE)
public class SingleValueMap {
	MultiValueMap<String, String> multis = new LinkedMultiValueMap<>();

	public SingleValueMap() {
	}

	SingleValueMap(MultiValueMap<String, String> multis) {
		Assert.notNull(multis);
		this.multis = multis;
	}

	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static SingleValueMap deserialize(Map<String, Object> map) {
		SingleValueMap singles = new SingleValueMap();
		if (map == null || map.isEmpty()) {
			return singles;
		}

		map.entrySet().stream().forEach(e -> singles.add(e.getKey(), e.getValue()));
		return singles;
	}

	void add(String key, Object value) {
		if (value instanceof String) {
			add(key, (String) value);
		} else if (value==null||value instanceof List) {
			add(key, (List<String>) value);
		}else{
			throw new IllegalArgumentException("cannot add "+value+" only String or List<String> is allowed");
		}
	}

	public void add(String key, String value) {
		multis.add(key, value);
	}

	public void add(String key, List<String> value) {
		multis.put(key, value);
	}

	@JsonAnyGetter()
	public Map<String, String> toMap() {
		return multis.toSingleValueMap();
	}

	public MultiValueMap<String, String> toMultiValueMap() {
		return multis;
	}

	/**
	 * to return the first value of the header with the given name
	 *
	 * @param name
	 * @return
	 */
	public String getFirst(String name) {
		return multis.getFirst(name);
	}

	public int size() {
		return multis.size();
	}

	/**
	 * @return true, if this map contains multiple values for any key
	 */
	public boolean containsMulti() {
		return multis.entrySet()
				.stream()
				.filter(e -> e.getValue().size() > 1)
				.findAny()
				.isPresent();
	}
}
