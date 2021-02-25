package com.mictale.jsonite.stream;

import com.mictale.jsonite.JsonArray;
import com.mictale.jsonite.JsonBuilder;
import com.mictale.jsonite.JsonObject;
import com.mictale.jsonite.JsonString;
import com.mictale.jsonite.JsonValue;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class TransformTest {

    @Test
    public void testParseTrue() {
        JsonValue value = Transformation.parse("true");
        assertThat(value, sameInstance(JsonValue.TRUE));
    }

    @Test
    public void testParseFalse() {
        JsonValue value = Transformation.parse("false");
        assertThat(value, sameInstance(JsonValue.FALSE));
    }

    @Test
    public void testParseNull() {
        JsonValue value = Transformation.parse("null");
        assertThat(value, sameInstance(JsonValue.NULL));
    }

    @Test
    public void testParseString() {
        JsonValue value = Transformation.parse("\"foo\"");
        assertThat(value, is(JsonString.of("foo")));
    }

    @Test
    public void testParseEmptyString() {
        JsonValue value = Transformation.parse("\"\"");
        assertThat(value, is(JsonString.of("")));
    }

    @Test
    public void testParseSlashString() {
        JsonValue value = Transformation.parse("\"foo\\/bar\"");
        assertThat(value, is(JsonString.of("foo/bar")));
    }

    @Test
    public void testParseArray() {
        JsonValue value = Transformation.parse("[1, \"foo\", 3]");
        /*
        JsonValue arr = JsonBuilder.withArray().
                add(1).
                add("foo").
                add(3).
                create();

        assertThat(value, is(equalTo(arr)));
        */
    }

    @Test
    public void testParseEmptyArray() {
        JsonValue value = Transformation.parse("[]");
        assertThat(value, is(instanceOf(JsonArray.class)));
        JsonArray arr = value.asArray();
        assertThat(arr.size(), is(0));
    }

    @Test
    public void testObjectInArray() {
        JsonValue value = Transformation.parse("[{}, {}, {}]");

        JsonValue arr = JsonBuilder.withArray().
                beginObject().endObject().
                beginObject().endObject().
                beginObject().endObject().
                endArray().
                value();

        assertThat(value, is(equalTo(arr)));

    }

    @Test
    public void testParseObject() {
        JsonValue value = Transformation.parse("{\"foo\": 1, \"bar\": false}");
        assertThat(value, is(instanceOf(JsonObject.class)));

        JsonValue expected = JsonBuilder.withObject().
                put("foo", 1).
                put("bar", false).
                endObject().value();

        assertThat(value, is(equalTo(expected)));
        JsonObject obj = value.asObject();
        assertThat(obj.size(), is(2));
    }

    @Test(expected = BrokenStreamException.class)
    public void testParseObjectBad() {
        Transformation.parse("{\"foo\": 1, \"bar\" false}");
    }
}
