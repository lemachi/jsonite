/**
 *<h2>Package Specification</h2>
 * 
 * This is the JSON streaming API. It provides streaming access to JSON content.
 * The smallest piece of information inside a stream that can be transferred is a
 * {@link com.mictale.jsonite.stream.Event}.
 * <p>
 * A JSON stream either implements {@link com.mictale.jsonite.stream.Producer} when
 * it produces events that can be read or {@link com.mictale.jsonite.stream.Consumer}
 * when it consumes events that can be written to the stream.
 * <p>
 * The {@link com.mictale.jsonite.stream.Transformation} class provides utility
 * methods to copy events from a {@link com.mictale.jsonite.stream.Producer} to a 
 * {@link com.mictale.jsonite.stream.Consumer}.
 * <p>
 * This package provides common implementations for both {@link com.mictale.jsonite.stream.Producer}s
 * and {@link com.mictale.jsonite.stream.Consumer}s that can be used
 * out of the box. These are {@link com.mictale.jsonite.stream.JsonStreamConsumer} and
 * {@link com.mictale.jsonite.stream.JsonValueConsumer} to consume events and
 * {@link com.mictale.jsonite.stream.JsonStreamProducer} and {@link com.mictale.jsonite.stream.JsonValueProducer}
 * to produce events.
 * <p>
 * 
 * <h2>Stream Events</h2>
 * 
 * Events are a sequential description of a tree of JSON values. For every tree of values,
 * there is exactly one sequence of events to describe the tree. Every event has an event type
 * defined in {@link com.mictale.jsonite.stream.EventType}. For example, the JSON tree
 * 
 * <pre>
 * {
 *   "package": "com.mictale.jsonite",
 *   "version": 1.0
 * }
 * </pre>
 * 
 * would be represented by the events
 * 
 * <pre>
 * START_OBJECT,
 * MEMBER_NAME,
 * PRIMITIVE,
 * MEMBER_NAME,
 * PRIMITIVE,
 * END_OBJECT 
 * </pre>
 * 
 * The values of the events (as returned by {@link com.mictale.jsonite.stream.Event#getValue()})
 * contain the actual values of the names and values.
 */
package com.mictale.jsonite.stream;

