package com.prefabware.tec.spring.api.jackson;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.prefabware.tec.commons.spring.string.Template;
import org.springframework.util.Assert;

/**
 * to handle JSON strings <br>
 * Json is unmutable
 * supports
 * <ul>
 * <li>usage of single quotes instead of double quotes</li>
 * <li>placeholder surrounded by ${} which can be replaced by actual values using {@link Json#expand(String, String)}</li>
 * <li>comparing JSON strings, ignoring different property orders, line breaks and whitespace</li>
 * </ul>
 * <p>
 * Created by stefan on 14.10.16.
 */
public class Json {
	static Pattern QUOTED = Pattern.compile("\\\"(?<value>.*)\\\"");
	static String GROUP_VALUE = "value";
	static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		//automatically register modules on the classpath
		//that can be problematic, if a module was unrecognized added and now makes trouble
		objectMapper.findAndRegisterModules();
	}

	final JsonNode node;

	public static Json of(Object object) {
		try {
			return new Json(objectMapper.writeValueAsString(object));
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("could not convert to json object :  " + object, e);
		}
	}

	public static Json of(String json) {
		return new Json(json);
	}

	public static Json of(JsonNode json) {
		return new Json(json.toString().replace("\"", "'"));
	}

	/**
	 * @param singleQuoted a JSON string using single quotes ['] instead of ["]
	 */
	Json(String singleQuoted) {
		Assert.notNull(singleQuoted, "JSON string must not be null");
		this.value = new Template(singleQuoted.replace("'", "\""), "${", "}");
		this.node = toNode(this.value.toString());
	}

	Template value;

	/**
	 * to convert the given JSOn to its basic string representation
	 * to allow
	 *
	 * @param json
	 * @return
	 */
	JsonNode toNode(String json) {
		try {
			return objectMapper.readTree(json);
		} catch (IOException e) {
			throw new IllegalArgumentException("could not read json " + json, e);
		}
	}

	public Stream<Map.Entry<String, JsonNode>> fields() {
		Iterator<Map.Entry<String, JsonNode>> fields = this.node.fields();
		Iterable<Map.Entry<String, JsonNode>> iterable = () -> fields;
		return StreamSupport.stream(iterable.spliterator(), false);
	}

	public <T> T toObject(Class<T> type) {
		try {
			return objectMapper.treeToValue(node, type);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("could not convert jsonNode to " + type + node, e);
		}
	}

	/**
	 * array properties with different order of elements are not considered to be equal
	 *
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Json json = (Json) o;
		return node.equals(json.node);

	}

	@Override
	public int hashCode() {
		return node.hashCode();
	}

	@Override
	public String toString() {
		return value.toString();
	}

	public Json expand(String name, String value) {
		String expanded = this.value.expand(name, value);

		return Json.of(expanded);
	}

	/**
	 * to find the property with the given name
	 *
	 * @param propertyName
	 * @return
	 * @throws IllegalArgumentException if the property cannot be found
	 */
	public String find(String propertyName) {
		return get(propertyName, true);
	}

	/**
	 * to get the property with the given name
	 *
	 * @param propertyName
	 * @return - the value of the property, returns null if not found
	 * @throws - does not throw an Exception if the property cannot be found
	 */
	public String get(String propertyName) {
		JsonNode at = getNode(propertyName);
		if (at instanceof MissingNode) {
			return null;
		} else if (at instanceof NullNode) {
			return null;
		} else if (at instanceof TextNode) {
			return at.textValue();
		}
		return at.toString();
	}

	public JsonNode getNode(String propertyName) {
		JsonPointer pointer = pointer(propertyName);
		return this.node.at(pointer);
	}

	JsonNode get(String property, JsonNode owner) {
		if (owner == null) {
			return null;
		}
		return owner.get(property);
	}

	JsonPointer pointer(String nestedProperty) {
		List<String> properties = unnest(nestedProperty);
		return JsonPointer.compile("/" + properties.stream().collect(Collectors.joining("/")));

	}

	List<String> unnest(String nestedProperty) {
		Assert.notNull(nestedProperty, "property name may not be null");
		return Arrays.asList(nestedProperty.split("\\."));
	}

	String unquote(String value) {
		if (value == null) {
			return value;
		}
		Matcher matcher = QUOTED.matcher(value);
		if (matcher.matches()) {
			return matcher.group(GROUP_VALUE);
		} else {
			return value;
		}
	}


	String get(String propertyName, boolean thrw) {
		JsonNode propertyNode = this.node.get(propertyName);
		return get(propertyName, propertyNode, thrw);
	}

	private String get(String propertyName, JsonNode owner, boolean thrw) {
		if (owner == null) {
			//the node does not exist
			//#find considers this an error
			//#get not
			if (thrw == true) {
				throw new IllegalArgumentException("cannot find property [" + propertyName + "] in json " + this.node);
			} else {
				return null;
			}
		} else if (owner.isNull()) {
			//the node exists but has value null
			return null;
		}
		return owner.toString();
	}

	/**
	 * to set the property to the value
	 *
	 * @param propertyName - simple or nested property name
	 * @param value
	 * @return the chamged Json, you MUST continue with the returned object, this will not necessarily changed
	 */
	public Json set(String propertyName, CharSequence value) {
		JsonPointer pointer = pointer(propertyName);
		JsonPointer head = pointer.head();
		ObjectNode parent = (ObjectNode) this.node.at(head);
		parent.put(pointer.last().toString().replace("/", ""), toString(value));
		if (parent == this.node) {
			return Json.of(parent.toString());
		}
		return Json.of(this.node);
	}

	/**
	 * in oposite to {@link Objects#toString(Object)}, which returns a String="null" for null,
	 * we need null for null
	 * @param value
	 * @return
	 */
	String toString(CharSequence value) {
		if(value==null){return null;}
		return value.toString();
	}
}
