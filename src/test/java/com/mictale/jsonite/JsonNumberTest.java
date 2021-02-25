package com.mictale.jsonite;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class JsonNumberTest {

    @Test(expected = JsonConversionException.class)
    public void testNumberAsBoolean() {
        JsonNumber.of(123).asBoolean();
    }

    @Test
    public void testType() {
        assertThat(JsonNumber.of(123).getType(), sameInstance(JsonType.NUMBER));
    }

}
