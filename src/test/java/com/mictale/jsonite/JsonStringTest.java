package com.mictale.jsonite;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsInstanceOf.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.CoreMatchers.*;

public class JsonStringTest {

    @Test(expected = JsonConversionException.class)
    public void testStringAsBoolean() {
        JsonString.of("Hello").asBoolean();
    }

    @Test
    public void stringEmpty() {
        JsonString empty = JsonString.of("").asString();
        assertThat(empty.stringValue(), equalTo(""));
    }

    @Test
    public void type() {
        assertThat(JsonString.of("foo").getType(), sameInstance(JsonType.STRING));
    }

}
