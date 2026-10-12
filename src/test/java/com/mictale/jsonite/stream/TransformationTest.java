package com.mictale.jsonite.stream;

import com.mictale.jsonite.JsonValue;

import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class TransformationTest {

    private static final String JSON = "{\"a\":[1,2,{\"b\":true}],\"c\":\"d\",\"e\":null}";

    @Test
    public void parsesStringsAndReaders() {
        assertThat(Transformation.parse(JSON), equalTo(JsonValue.parse(JSON)));
        assertThat(Transformation.parse(new StringReader(JSON)), equalTo(JsonValue.parse(JSON)));
    }

    @Test
    public void compactRoundTrip() {
        String s = Transformation.asString(new JsonValueProducer(JsonValue.parse(JSON)), false);
        assertThat(s, not(containsString("\n")));
        assertThat(JsonValue.parse(s), equalTo(JsonValue.parse(JSON)));
    }

    @Test
    public void prettyRoundTrip() {
        String s = Transformation.asString(new JsonValueProducer(JsonValue.parse(JSON)), true);
        assertThat(s, containsString("\n    "));
        assertThat(JsonValue.parse(s), equalTo(JsonValue.parse(JSON)));
        assertThat(Transformation.asPrettyString(new JsonValueProducer(JsonValue.parse(JSON))), equalTo(s));
    }

    @Test
    public void prettyCopyOfAValue() {
        StringWriter w = new StringWriter();
        Transformation.prettyCopy(w, JsonValue.parse(JSON));
        assertThat(JsonValue.parse(w.toString()), equalTo(JsonValue.parse(JSON)));
    }

    @Test
    public void copiesBetweenProducersAndConsumers() {
        JsonValueConsumer c = new JsonValueConsumer();
        Transformation.copy(c, new JsonStreamProducer(JSON));
        assertThat(c.getValue(), equalTo(JsonValue.parse(JSON)));

        JsonValueConsumer c2 = new JsonValueConsumer();
        Transformation.copy(c2, JsonValue.parse(JSON));
        assertThat(c2.getValue(), equalTo(JsonValue.parse(JSON)));

        assertThat(Transformation.asValue(new JsonStreamProducer(new StringReader(JSON))), equalTo(JsonValue.parse(JSON)));
    }
}
