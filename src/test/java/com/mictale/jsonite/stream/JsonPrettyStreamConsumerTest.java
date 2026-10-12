package com.mictale.jsonite.stream;

import com.mictale.jsonite.JsonValue;

import org.junit.Test;

import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonPrettyStreamConsumerTest {

    private static String pretty(String json) {
        StringWriter w = new StringWriter();
        Transformation.prettyCopy(w, JsonValue.parse(json));
        return w.toString();
    }

    @Test
    public void indentsNestedContainers() {
        assertThat(pretty("{\"a\":[1,2],\"b\":{\"c\":true}}"), equalTo(
                "{\n" +
                "    \"a\": [\n" +
                "        1,\n" +
                "        2\n" +
                "    ],\n" +
                "    \"b\": {\n" +
                "        \"c\": true\n" +
                "    }\n" +
                "}"));
    }

    @Test
    public void emptyContainersStayOnOneLine() {
        assertThat(pretty("{\"a\":[],\"b\":{}}"), equalTo(
                "{\n" +
                "    \"a\": [],\n" +
                "    \"b\": {}\n" +
                "}"));
    }

    @Test
    public void primitivesAreUnchanged() {
        assertThat(pretty("42"), equalTo("42"));
        assertThat(pretty("\"x\""), equalTo("\"x\""));
    }
}
