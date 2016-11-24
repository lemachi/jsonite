package com.mictale.jsonite;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class JsonBuilderTest {

    @Test
    public void buildObject() {
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
    public void buildArray() {
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
    public void buildArrayOfObjects() {
        JsonArray o = JsonBuilder.withArray().
                object().endObject().
                object().endObject().
                endArray().
                value().asArray();

        assertThat(o.size(), is(equalTo(2)));
        assertThat(o.get(0).asObject(), is(equalTo(new JsonObject())));
        assertThat(o.get(1).asObject(), is(equalTo(new JsonObject())));
    }

    @Test
    public void complexObjects() {
        JsonObject o = JsonBuilder.withObject().
                    put("foo").object().
                        put("bar").object().
                            endObject().
                        put("bar", 12).
                    endObject().
                endObject().
                value().
                asObject();

        assertThat(o.size(), is(equalTo(1)));
    }

}
