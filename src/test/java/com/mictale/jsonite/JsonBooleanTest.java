package com.mictale.jsonite;

import org.junit.Test;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.sameInstance;

public class JsonBooleanTest {

    @Test
    public void asBoolean() {
        assertThat(JsonValue.FALSE.asBoolean(), sameInstance(JsonValue.FALSE));
        assertThat(JsonValue.TRUE.asBoolean(), sameInstance(JsonValue.TRUE));
        assertThat(JsonValue.FALSE.asBoolean(), not(JsonValue.TRUE));
        assertThat(JsonValue.TRUE.asBoolean(), not(JsonValue.FALSE));
    }

    @Test(expected = JsonConversionException.class)
    public void asArray() {
        JsonValue.FALSE.asArray();
    }

    @Test
    public void type() {
        assertThat(JsonValue.TRUE.getType(), sameInstance(JsonType.BOOLEAN));
        assertThat(JsonValue.FALSE.getType(), sameInstance(JsonType.BOOLEAN));
    }

}
