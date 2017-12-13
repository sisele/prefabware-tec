package com.prefabware.tec.commons.map;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.*;

import static org.junit.Assert.assertNotNull;

/**
 * Created by stefan on 05.12.17.
 */
public class MapTest {

	/***
	 * to wrap a map
	 * make this a normal class
	 */
	public static class MapWrapper {
		public MapWrapper(Map<String, Object> map) {
			this.map = map;
		}

		public String getType() {
			return get("__type");
		}

		public String getId() {
			return get("id");
		}

		/**
		 * to return the id, if _type and id are not null
		 * @return
		 */
		public String getIdIfPresent() {
			if (getType() != null && getId() != null) {
				return getId();
			}
			return null;
		}

		/**
		 * to get a value from the map, casted to the expected type
		 * @param key
		 * @param <T>
		 * @return
		 */
		<T> T get(String key) {
			Object o = map.get(key);
			return (T) o;
		}

		final Map<String, Object> map;
	}

	@Test
	public void testMapHappy() throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("__type", "EntityReference:whatever");
		map.put("id", "lloks-like-uuid");

		MapWrapper wrapper = new MapWrapper(map);
		assertNotNull(wrapper.getId());
		assertNotNull(wrapper.getType());
		assertNotNull(wrapper.getIdIfPresent());


	}
}
