package com.mictale.jsonite;

public final class JsonUtils {

	/**
	 * Returns the first JSON object or an empty object if all values are null.
	 * 
	 * @param values contains the objects to test.
	 * @return the first object.
	 */
	public static JsonObject coalesceObjects(JsonValue...values) {
		for (int i = 0; i < values.length; i++) {
			JsonValue v = values[i];
			if (v != null && v.isObject()) {
				return v.asObject();
			}
		}
		
		return new JsonObject();
	}
}
