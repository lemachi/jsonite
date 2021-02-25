package com.mictale.jsonite;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class JsonBuilderTest {

    @Test
    public void testBuildObject() {
        JsonObject o = JsonBuilder.withObject().
                put("foo", 1).
                put("bar", 2).
                endObject().
                value().asObject();

        assertThat(o.size(), is(equalTo(2)));
        assertThat(o, hasEntry("foo", JsonValue.of(1)));
        assertThat(o, hasEntry("bar", JsonValue.of(2)));
    }

    @Test
    public void testBuildArray() {
        JsonArray o = JsonBuilder.withArray().
                value("foo").
                value("bar").
                endArray().
                value().asArray();

        assertThat(o.size(), is(equalTo(2)));
        assertThat(o, hasItem(JsonString.of("foo")));
        assertThat(o, hasItem(JsonString.of("bar")));
    }

    @Test
    public void testBuildArrayOfObjects() {
        JsonArray o = JsonBuilder.withArray().
                beginObject().endObject().
                beginObject().endObject().
                endArray().
                value().asArray();

        assertThat(o.size(), is(equalTo(2)));
        assertThat(o.get(0).asObject(), is(equalTo(new JsonObject())));
        assertThat(o.get(1).asObject(), is(equalTo(new JsonObject())));
    }

    @Test
    public void testComplexObjects() {
        JsonObject o = JsonBuilder.withObject().
                    put("foo").beginObject().
                        put("bar").beginObject().
                            endObject().
                        put("bar", 12).
                    endObject().
                endObject().
                value().
                asObject();

        assertThat(o.size(), is(equalTo(1)));
    }

}
