package com.mictale.jsonite;

import com.mictale.jsonite.stream.Event;
import com.mictale.jsonite.stream.EventType;
import com.mictale.jsonite.stream.JsonValueConsumer;

/**
 * Create a hierarchy of JSON values.
 */
public final class JsonBuilder {

    private JsonValueConsumer consumer;

    private JsonBuilder() {
        consumer = new JsonValueConsumer();
    }

    public static JsonBuilder withArray() {
        return new JsonBuilder().array();
    }

    public static JsonBuilder withObject() {
        return new JsonBuilder().object();
    }

    public JsonBuilder array() {
        consumer.append(new Event(EventType.START_ARRAY, null, null));
        return this;
    }

    public JsonBuilder endArray() {
        consumer.append(new Event(EventType.END_ARRAY, null, null));
        return this;
    }

    public JsonBuilder object() {
        consumer.append(new Event(EventType.START_OBJECT, null, null));
        return this;
    }

    public JsonBuilder endObject() {
        consumer.append(new Event(EventType.END_OBJECT, null, null));
        return this;
    }

    public JsonBuilder put(String key) {
        consumer.append(new Event(EventType.MEMBER_NAME, JsonString.of(key), null));
        return this;
    }

    public JsonBuilder put(String key, Object value) {
        return put(key, JsonValue.of(value));
    }

    public JsonBuilder put(String key, JsonValue value) {
        consumer.append(new Event(EventType.MEMBER_NAME, JsonString.of(key), null));
        consumer.append(new Event(EventType.PRIMITIVE, value, null));
        return this;
    }

    public JsonBuilder value(Object o) {
        consumer.append(new Event(EventType.PRIMITIVE, JsonValue.of(o), null));
        return this;
    }

    public JsonValue value() {
        return consumer.getValue();
    }
}
