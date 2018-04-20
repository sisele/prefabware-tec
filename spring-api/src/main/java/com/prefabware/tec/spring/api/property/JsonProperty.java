package com.prefabware.tec.spring.api.property;


import com.prefabware.tec.spring.api.jackson.Json;

/**
 * to receive a json object as property value
 * than get the json converted into a Java object using {@link #value(Class)}
 * Created by stefan on 06.10.16.
 */
public class JsonProperty {
	public final String json;

	public JsonProperty(String json) {
		this.json = json;
	}

	/**
	 * to get the JSON of the property converted into an object of the given type
	 * @param type
	 * @param <T>
	 * @return
	 */
	public <T> T value(Class<T>type){
			return Json.of(json).toObject(type);
	};
}
