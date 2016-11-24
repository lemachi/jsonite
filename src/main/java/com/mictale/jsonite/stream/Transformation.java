package com.mictale.jsonite.stream;

import com.mictale.jsonite.JsonValue;

import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Provides transformation utilities.
 * 
 * @author michael@mictale.com
 */
public final class Transformation {

	private Transformation() {
	}

	/**
	 * Copies the contents of the specified {@link Producer} to a specified {@link Consumer}.
	 * 
	 * @param consumer is the consumer of the operation.
	 * @param producer is the producer of the operation.
	 * @throws BrokenStreamException
	 */
	public static void copy(Consumer consumer, Producer producer) throws BrokenStreamException {
		producer.copyTo(consumer);
	}

	/**
	 * Copies the tokens from the specified producer to the specified {@link Consumer}.
	 * 
	 * This implementation simply wraps the specified {@link Reader} into a {@link Producer}
	 * and calls {@link #copy(Consumer, Producer)}.
	 * 
	 * @param consumer is the consumer of the operation.
	 * @param producer is the producer of the operation.
	 * @throws BrokenStreamException
	 */
	public static void copy(Consumer consumer, Reader producer) throws BrokenStreamException {
		copy(consumer, new JsonStreamProducer(producer));
	}
	
	/**
	 * Parses the specified JSON string to the specified {@link Consumer}.
	 * 
	 * This implementation simply wraps the specified {@link Reader} into a {@link Producer}
	 * and calls {@link #copy(Consumer, Producer)}.
	 *
	 * @param consumer is the consumer of the operation.
	 * @param json is the JSON string.
	 * @throws BrokenStreamException
	 */
	public static void copy(Consumer consumer, String json) throws BrokenStreamException {
		copy(consumer, new JsonStreamProducer(json));
	}

	/**
	 * Copies the specified {@link Producer} to the specified {@link Consumer}.
	 * 
	 * This implementation simply wraps the specified {@link Writer} into a {@link Consumer}
	 * and calls {@link #copy(Consumer, Producer)}.
	 * 
	 * @param consumer is the consumer of this operation.
	 * @param producer is the producer of the operation.
	 * @throws BrokenStreamException
	 */
	public static void copy(Writer consumer, Producer producer) throws BrokenStreamException {
		copy(new JsonStreamConsumer(consumer), producer);
	}

    public static void prettyCopy(Writer consumer, Producer producer) throws BrokenStreamException {
        copy(new JsonPrettyStreamConsumer(consumer), producer);
    }

    /**
     * Copies the specified {@link JsonValue} to the specified {@code Writer}.
     *
     * This implementation simply wraps the specified {@link Writer} into a {@link Consumer}
     * and calls {@link #copy(Consumer, Producer)}.
     *
     * @param consumer is the consumer of this operation.
     * @param value is the JSON value to copy.
     * @throws BrokenStreamException
     */
    public static void copy(Writer consumer, JsonValue value) throws BrokenStreamException {
        copy(new JsonStreamConsumer(consumer), new JsonValueProducer(value));
    }

    public static void prettyCopy(Writer consumer, JsonValue value) throws BrokenStreamException {
        copy(new JsonPrettyStreamConsumer(consumer), new JsonValueProducer(value));
    }

	/**
	 * Generates a JSON string produced by {@link Producer}.
	 * 
	 * @param producer creates the contents.
	 * @param prettify specifies if the JSON should be pretty (otherwise it will be short).
	 * @return the JSON string.
	 */
	public static String asString(Producer producer, boolean prettify) {
		StringWriter sw = new StringWriter();
		copy(sw, producer);
		return sw.toString();
	}

    public static String asPrettyString(Producer producer) {
        StringWriter sw = new StringWriter();
        prettyCopy(sw, producer);
        return sw.toString();
    }

	/**
	 * Parses the contents of the {@link Producer} into a {@link JsonValue}.
	 * 
	 * @param producer generates JSON events.
	 * @return the {@link JsonValue} that has been produced.
	 */
	public static JsonValue asValue(Producer producer) {
		JsonValueConsumer c = new JsonValueConsumer();
		copy(c, producer);
		return c.getValue();
	}
	
	/**
	 * Parses JSON content into a {@link JsonValue}.
	 * 
	 * @param reader contains the JSON content.
	 * @return the {@link JsonValue}.
	 */
	public static JsonValue parse(Reader reader) {
		JsonValueConsumer c = new JsonValueConsumer();
		copy(c, reader);
		return c.getValue();
	}

	/**
	 * Parses JSON content into a {@link JsonValue}.
	 * 
	 * @param str contains the JSON content.
	 * @return the {@link JsonValue}.
	 */
	public static JsonValue parse(String str) {
		JsonValueConsumer c = new JsonValueConsumer();
		copy(c, str);
		return c.getValue();
	}
}
