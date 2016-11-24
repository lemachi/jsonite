package com.mictale.jsonite;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsInstanceOf.*;
import static org.hamcrest.core.IsNot.*;

public class JsonValueTest {

    @Test
    public void testOfTrue() {
        assertThat(JsonValue.of(true), is(JsonValue.TRUE));
    }

    @Test
    public void testOfFalse() {
        assertThat(JsonValue.of(false), is(JsonValue.FALSE));
    }

    @Test
    public void testOfNull() {
        assertThat(JsonValue.of(null), is(JsonValue.NULL));
    }

    @Test
    public void testOfString() {
        assertThat(JsonValue.of("jsonite"), is(instanceOf(JsonString.class)));
    }
}
