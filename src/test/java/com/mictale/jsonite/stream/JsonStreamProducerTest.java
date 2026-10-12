package com.mictale.jsonite.stream;

import com.mictale.jsonite.JsonValue;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class JsonStreamProducerTest {

    @Test
    public void testTab() {
        String json = "\"\\t\"";
        JsonStreamProducer producer = new JsonStreamProducer(json);
        JsonValueConsumer consumer = new JsonValueConsumer();
        producer.copyTo(consumer);

        JsonValue value = consumer.getValue();
        assertTrue(value.isString());
        assertThat(value.stringValue(), equalTo("\t"));
    }

    @Test
    public void testUnicode() {
        String json = "\"\\u4389\"";
        JsonStreamProducer producer = new JsonStreamProducer(json);
        JsonValueConsumer consumer = new JsonValueConsumer();
        producer.copyTo(consumer);

        JsonValue value = consumer.getValue();
        assertTrue(value.isString());
        assertThat(value.stringValue(), equalTo("\u4389"));
    }
}
