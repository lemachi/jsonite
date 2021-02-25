package com.mictale.jsonite;

import com.mictale.jsonite.stream.Consumer;
import com.mictale.jsonite.stream.Token;
import com.mictale.jsonite.stream.TokenType;
import com.mictale.jsonite.stream.JsonValueConsumer;

/**
 * Create a hierarchy of JSON values.
 */
public final class JsonBuilder {

    private final Consumer consumer;

    private JsonBuilder() {
        this(new JsonValueConsumer());
    }

    private JsonBuilder(Consumer consumer) {
        this.consumer = consumer;
    }

    public static JsonBuilder fromConsumer(Consumer consumer) {
        return new JsonBuilder(consumer);
    }

    public static JsonBuilder withArray() {
        return new JsonBuilder().beginArray();
    }

    public static JsonBuilder withObject() {
        return new JsonBuilder().beginObject();
    }

    public JsonBuilder beginArray() {
        consumer.append(new Token(TokenType.START_ARRAY, null, null));
        return this;
    }

    public JsonBuilder endArray() {
        consumer.append(new Token(TokenType.END_ARRAY, null, null));
        return this;
    }

    public JsonBuilder beginObject() {
        consumer.append(new Token(TokenType.START_OBJECT, null, null));
        return this;
    }

    public JsonBuilder endObject() {
        consumer.append(new Token(TokenType.END_OBJECT, null, null));
        return this;
    }

    public JsonBuilder put(String key) {
        consumer.append(new Token(TokenType.MEMBER_NAME, JsonString.of(key), null));
        return this;
    }

    public JsonBuilder put(String key, Object value) {
        return put(key, JsonValue.of(value));
    }

    public JsonBuilder put(String key, JsonValue value) {
        consumer.append(new Token(TokenType.MEMBER_NAME, JsonString.of(key), null));
        consumer.append(new Token(TokenType.PRIMITIVE, value, null));
        return this;
    }

    public JsonBuilder value(Object o) {
        consumer.append(new Token(TokenType.PRIMITIVE, JsonValue.of(o), null));
        return this;
    }

    public JsonValue value() {
        return ((JsonValueConsumer)consumer).getValue();
    }

    public Consumer getConsumer() {
        return consumer;
    }
}
