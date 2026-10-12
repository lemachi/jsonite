package com.mictale.jsonite.stream;

import com.mictale.jsonite.JsonException;
import com.mictale.jsonite.JsonValue;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonValueConsumerTest {

    @Test
    public void completeStreamYieldsTheValue() {
        JsonValueConsumer c = new JsonValueConsumer();
        c.append(new Token(TokenType.PRIMITIVE, JsonValue.of(42)));
        assertThat(c.getValue().intValue(), equalTo(42));
    }

    @Test(expected = JsonException.class)
    public void incompleteStreamThrowsJsonException() {
        JsonValueConsumer c = new JsonValueConsumer();
        c.append(new Token(TokenType.START_OBJECT, null));
        c.getValue();
    }
}
