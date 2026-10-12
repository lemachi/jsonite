package com.mictale.jsonite;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonUtilsTest {

    @Test
    public void returnsTheFirstObject() {
        JsonObject first = JsonValue.parse("{\"a\":1}").asObject();
        JsonObject second = JsonValue.parse("{\"b\":2}").asObject();
        assertThat(JsonUtils.coalesceObjects(null, JsonValue.of(3), first, second) == first, equalTo(true));
    }

    @Test
    public void returnsAnEmptyObjectWhenThereIsNone() {
        assertThat(JsonUtils.coalesceObjects(null, JsonValue.NULL).size(), equalTo(0));
        assertThat(JsonUtils.coalesceObjects().size(), equalTo(0));
    }
}
