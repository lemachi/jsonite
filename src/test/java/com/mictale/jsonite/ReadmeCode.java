package com.mictale.jsonite;

import com.mictale.jsonite.stream.BrokenStreamException;
import com.mictale.jsonite.stream.Consumer;
import com.mictale.jsonite.stream.Token;
import com.mictale.jsonite.stream.Transformation;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReadmeCode {

    @Test
    public void parseFooString() {
        JsonValue foo = JsonValue.parse("42");
        int i = foo.intValue();
        assertThat(i, equalTo(42));
    }

    @Test
    public void parseFooObject() {
        JsonValue foo = JsonValue.parse("{\"foo\":42}");
        JsonObject fooObj = foo.asObject();
        JsonValue v = fooObj.get("foo");
        int i = v.intValue();

        assertThat(i, equalTo(42));
    }

    @Test
    public void stringifyObject() {
        JsonObject obj = new JsonObject();
        obj.put("foo", 42);
        obj.put("bar", "foobar");
        String str = obj.toString(); // {"foo":42,"bar":"foobar"}
    }

    @Test
    public void dumpEvents() {
        JsonValue foo = JsonValue.parse("{\"foo\":[1,42]}");
        final StringBuilder buffer = new StringBuilder();

        Transformation.copy(new Consumer() {
            @Override
            public void append(Token token) throws BrokenStreamException {
                buffer.append(token);
                buffer.append(System.lineSeparator());
            }
        }, foo);

        /*
        START_OBJECT
        MEMBER_NAME:"foo"
        START_ARRAY
        PRIMITIVE:1
        PRIMITIVE:42
        END_ARRAY
        END_OBJECT
         */

    }

}
