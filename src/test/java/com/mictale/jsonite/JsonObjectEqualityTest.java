package com.mictale.jsonite;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonObjectEqualityTest {

    @Test
    public void memberOrderDoesNotMatter() {
        JsonValue a = JsonValue.parse("{\"x\":1,\"y\":2}");
        JsonValue b = JsonValue.parse("{\"y\":2,\"x\":1}");
        assertThat(a, equalTo(b));
        assertThat(a.hashCode(), equalTo(b.hashCode()));
    }

    @Test
    public void differentValuesDiffer() {
        assertThat(JsonValue.parse("{\"x\":1}"), not(equalTo(JsonValue.parse("{\"x\":2}"))));
    }

    @Test
    public void objectsWorkAsHashKeys() {
        Set<JsonValue> set = new HashSet<>();
        set.add(JsonValue.parse("{\"x\":[1,2]}"));
        assertThat(set.contains(JsonValue.parse("{\"x\":[1,2]}")), equalTo(true));
    }
}
