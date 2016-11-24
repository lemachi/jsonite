package com.mictale.jsonite.stream;

import com.mictale.jsonite.JsonArray;
import com.mictale.jsonite.JsonBuilder;
import com.mictale.jsonite.JsonConversionException;
import com.mictale.jsonite.JsonNull;
import com.mictale.jsonite.JsonNumber;
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
    public void parseTrue() {
        JsonValue value = Transformation.parse("true");
        assertThat(value, sameInstance(JsonValue.TRUE));
    }

    @Test
    public void parseFalse() {
        JsonValue value = Transformation.parse("false");
        assertThat(value, sameInstance(JsonValue.FALSE));
    }

    @Test
    public void parseNull() {
        JsonValue value = Transformation.parse("null");
        assertThat(value, sameInstance(JsonValue.NULL));
    }

    @Test
    public void parseString() {
        JsonValue value = Transformation.parse("\"foo\"");
        assertThat(value, is(JsonString.of("foo")));
    }

    @Test
    public void parseEmptyString() {
        JsonValue value = Transformation.parse("\"\"");
        assertThat(value, is(JsonString.of("")));
    }

    @Test
    public void parseSlashString() {
        JsonValue value = Transformation.parse("\"foo\\/bar\"");
        assertThat(value, is(JsonString.of("foo/bar")));
    }

    @Test
    public void parseArray() {
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
    public void parseEmptyArray() {
        JsonValue value = Transformation.parse("[]");
        assertThat(value, is(instanceOf(JsonArray.class)));
        JsonArray arr = value.asArray();
        assertThat(arr.size(), is(0));
    }

    @Test
    public void objectInArray() {
        JsonValue value = Transformation.parse("[{}, {}, {}]");

        JsonValue arr = JsonBuilder.withArray().
                    object().endObject().
                    object().endObject().
                    object().endObject().
                endArray().
                value();

        assertThat(value, is(equalTo(arr)));

    }

    @Test
    public void parseObject() {
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
    public void parseObjectBad() {
        Transformation.parse("{\"foo\": 1, \"bar\" false}");
    }
}
