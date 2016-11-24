package com.mictale.jsonite;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class JsonNullTest {

    @Test(expected = JsonConversionException.class)
    public void nullAsBoolean() {
        JsonValue.NULL.asBoolean();
    }

    @Test(expected = JsonConversionException.class)
    public void nullAsArray() {
        JsonValue.NULL.asArray();
    }

    @Test(expected = JsonConversionException.class)
    public void nullAsNumber() {
        JsonValue.NULL.asNumber();
    }

    @Test(expected = JsonConversionException.class)
    public void nullAsObject() {
        JsonValue.NULL.asObject();
    }

    @Test(expected = JsonConversionException.class)
    public void nullAsString() {
        JsonValue.NULL.asString();
    }

    @Test
    public void type() {
        assertThat(JsonValue.NULL.getType(), sameInstance(JsonType.NULL));
    }

}
