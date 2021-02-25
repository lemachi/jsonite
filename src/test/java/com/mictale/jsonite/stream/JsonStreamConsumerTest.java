package com.mictale.jsonite.stream;

import com.mictale.jsonite.JsonValue;

import org.junit.Test;

import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link JsonStreamConsumer}
 */
public class JsonStreamConsumerTest {

    @Test
    public void testAppendTrue() {
        StringWriter w = new StringWriter();
        JsonStreamConsumer c = new JsonStreamConsumer(w);
        c.append(new Event(EventType.PRIMITIVE, JsonValue.TRUE));
        assertThat(w.toString(), equalTo("true"));
    }

    @Test
    public void testAppendEmptyArray() {
        StringWriter w = new StringWriter();
        JsonStreamConsumer c = new JsonStreamConsumer(w);
        c.append(new Event(EventType.START_ARRAY, null));
        c.append(new Event(EventType.END_ARRAY, null));
        assertThat(w.toString(), equalTo("[]"));
    }

    @Test
    public void testAppendArray() {
        StringWriter w = new StringWriter();
        JsonStreamConsumer c = new JsonStreamConsumer(w);
        c.append(new Event(EventType.START_ARRAY, null));
        c.append(new Event(EventType.PRIMITIVE, JsonValue.of(1)));
        c.append(new Event(EventType.PRIMITIVE, JsonValue.of(2)));
        c.append(new Event(EventType.PRIMITIVE, JsonValue.of("foo")));
        c.append(new Event(EventType.END_ARRAY, null));
        assertThat(w.toString(), equalTo("[1,2,\"foo\"]"));
    }

    @Test
    public void testAppendObject() {
        StringWriter w = new StringWriter();
        JsonStreamConsumer c = new JsonStreamConsumer(w);
        c.append(new Event(EventType.START_OBJECT, null));
        c.append(new Event(EventType.MEMBER_NAME, JsonValue.of("foo")));
        c.append(new Event(EventType.PRIMITIVE, JsonValue.of(1)));
        c.append(new Event(EventType.MEMBER_NAME, JsonValue.of("bar")));
        c.append(new Event(EventType.PRIMITIVE, JsonValue.of(2)));
        c.append(new Event(EventType.END_OBJECT, null));
        assertThat(w.toString(), equalTo("{\"foo\":1,\"bar\":2}"));
    }

    @Test
    public void testAppendObjectPretty() {
        StringWriter w = new StringWriter();
        JsonStreamConsumer c = new JsonStreamConsumer(w);
        c.append(new Event(EventType.START_OBJECT, null));
        c.append(new Event(EventType.MEMBER_NAME, JsonValue.of("foo")));
        c.append(new Event(EventType.PRIMITIVE, JsonValue.of(1)));
        c.append(new Event(EventType.MEMBER_NAME, JsonValue.of("bar")));
        c.append(new Event(EventType.PRIMITIVE, JsonValue.of(2)));
        c.append(new Event(EventType.END_OBJECT, null));
        assertThat(w.toString(), equalTo("{\"foo\":1,\"bar\":2}"));
    }

}
