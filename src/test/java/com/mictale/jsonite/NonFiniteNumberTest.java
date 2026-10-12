package com.mictale.jsonite;

import com.mictale.jsonite.stream.BrokenStreamException;

import org.junit.Test;

import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * JSON has no NaN or infinity. Jsonite accepts them in memory, because
 * applications hold such values at run time, and refuses them only when a
 * value is written.
 */
public class NonFiniteNumberTest {

    @Test
    public void nanIsAcceptedInMemory() {
        JsonValue v = JsonValue.of(Double.NaN);
        assertThat(v, instanceOf(JsonNumber.class));
        assertThat(Double.isNaN(v.doubleValue()), equalTo(true));
    }

    @Test
    public void infinityIsAcceptedInMemory() {
        JsonValue v = JsonValue.of(Double.POSITIVE_INFINITY);
        assertThat(v.doubleValue(), equalTo(Double.POSITIVE_INFINITY));
    }

    @Test(expected = BrokenStreamException.class)
    public void nanIsRefusedWhenWritten() throws Exception {
        JsonValue.of(Double.NaN).writeTo(new StringWriter());
    }

    @Test(expected = BrokenStreamException.class)
    public void nanInsideAnObjectIsRefusedWhenWritten() throws Exception {
        JsonObject o = new JsonObject();
        o.put("x", JsonValue.of(Double.NEGATIVE_INFINITY));
        o.writeTo(new StringWriter());
    }

    @Test
    public void finiteNumbersAreWritten() throws Exception {
        StringWriter w = new StringWriter();
        JsonValue.of(1.5).writeTo(w);
        assertThat(w.toString(), equalTo("1.5"));
    }
}
