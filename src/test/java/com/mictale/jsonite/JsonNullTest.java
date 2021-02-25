package com.mictale.jsonite;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class JsonNullTest {

    @Test(expected = JsonConversionException.class)
    public void testNullAsBoolean() {
        JsonValue.NULL.asBoolean();
    }

    @Test(expected = JsonConversionException.class)
    public void testNullAsArray() {
        JsonValue.NULL.asArray();
    }

    @Test(expected = JsonConversionException.class)
    public void testNullAsNumber() {
        JsonValue.NULL.asNumber();
    }

    @Test(expected = JsonConversionException.class)
    public void testNullAsObject() {
        JsonValue.NULL.asObject();
    }

    @Test(expected = JsonConversionException.class)
    public void testNullAsString() {
        JsonValue.NULL.asString();
    }

    @Test
    public void testType() {
        assertThat(JsonValue.NULL.getType(), sameInstance(JsonType.NULL));
    }

}
