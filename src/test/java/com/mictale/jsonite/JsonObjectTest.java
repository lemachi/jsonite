package com.mictale.jsonite;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JsonObjectTest {

    @Test
    public void testSizeZero() {
        JsonObject obj = new JsonObject();
        assertThat(obj.size(), is(0));
    }

    @Test
    public void testSizeNonZero() {
        JsonObject obj = new JsonObject();
        obj.put("foo", "bar");
        assertThat(obj.size(), is(1));
    }

    @Test
    public void testOberwriteProperty() {
        JsonObject obj = new JsonObject();
        obj.put("foo", "bar");
        assertThat(obj.size(), is(1));
        obj.put("foo", 12);
        assertThat(obj.size(), is(1));
        assertThat(obj.get("foo"), is(JsonNumber.of(12)));
    }

    @Test
    public void testSetProperty() {
        JsonObject obj = new JsonObject();
        obj.put("foo", "bar");
        assertThat(obj.get("foo"), is(JsonString.of("bar")));
    }

    @Test
    public void testType() {
        JsonObject obj = new JsonObject();
        assertThat(obj.getType(), sameInstance(JsonType.OBJECT));
    }

    @Test
    public void testAsObject() {
        JsonObject obj = new JsonObject();
        assertThat(obj.asObject(), sameInstance(obj));
    }

    @Test(expected = JsonConversionException.class)
    public void testAsArray() {
        JsonObject obj = new JsonObject();
        obj.asArray();
    }

    @Test
    public void testClear() {
        JsonObject obj = new JsonObject();
        obj.put("foo", 1);
        obj.put("bar", "hello");
        obj.clear();
        assertThat(obj.size(), is(0));
    }

    @Test
    public void testContainsKey() {
        JsonObject obj = new JsonObject();
        obj.put("foo", 1);
        obj.put("bar", "hello");
        assertThat(obj.containsKey("foo"), is(true));
        assertThat(obj.containsKey("some"), is(false));
    }

    @Test
    public void testContainsValue() {
        JsonObject obj = new JsonObject();
        obj.put("foo", 1);
        obj.put("bar", "hello");
        assertThat(obj.containsValue(1), is(true));
        assertThat(obj.containsValue(2), is(false));
        assertThat(obj.containsValue("hello"), is(true));
    }

    @Test
    public void testGetWithDefault() {
        JsonObject obj = new JsonObject();
        final JsonValue n = JsonNumber.of(87293);
        final JsonValue a = JsonValue.of("a");
        obj.put("foo", n);
        obj.put("bar", "hello");
        assertThat(obj.get("foo", a), is(n));
        assertThat(obj.get("foo_", a), is(a));
    }

    @Test
    public void testMap() {
        JsonObject obj = new JsonObject();
        obj.put("foo", 123);
        obj.put("bar", "hello");

        assertThat(obj, hasEntry("foo", JsonValue.of(123)));
        assertThat(obj, hasEntry("bar", JsonValue.of("hello")));
    }

    @Test
    public void testRemove() {
        JsonObject obj = new JsonObject();
        obj.put("foo", 123);
        obj.put("bar", "hello");

        assertThat(obj.size(), is(2));

        assertThat(obj, hasEntry("foo", JsonValue.of(123)));
        assertThat(obj, hasEntry("bar", JsonValue.of("hello")));

        obj.remove("bar");
    }


}
