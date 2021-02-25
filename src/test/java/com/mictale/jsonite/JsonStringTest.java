package com.mictale.jsonite;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class JsonStringTest {

    @Test(expected = JsonConversionException.class)
    public void testStringAsBoolean() {
        JsonString.of("Hello").asBoolean();
    }

    @Test
    public void testStringEmpty() {
        JsonString empty = JsonString.of("").asString();
        assertThat(empty.stringValue(), equalTo(""));
    }

    @Test
    public void testType() {
        assertThat(JsonString.of("foo").getType(), sameInstance(JsonType.STRING));
    }

}
