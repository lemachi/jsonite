/**
 * <h2>Package Specification</h2>
 *
 * This is yet another implementation of the <em>Java Script Object Notation</em>em> (JSON), as
 * specified in section 15.12.1.2 of
 * <a href="http://www.ecma-international.org/publications/files/ECMA-ST/Ecma-262.pdf">ECMA-262</a>.
 * <p>
 * While many implementations of this standard exist for the Java programming language, I still
 * felt a motivation to add my own. The most prominent, <code>org.json</code> (and some others)
 * lack a common superclass for JSON types and they produce many compiler warnings due to the absence
 * of generics.
 * <p>
 * I did not follow the specification word by word. At obvious places, I took reasonable shortcuts,
 * e.g. when parsing a number I used Java's <code>Double.parseDouble()</code> instead of crafting my
 * own.
 * <p>
 * Most classes in the object tree extend {@link com.mictale.jsonite.JsonValue} and this class also
 * contains a factory method {@link com.mictale.jsonite.JsonValue#of(java.lang.Object)} to produce
 * new JSON instances from Java types.
 * <p>
 * A value can either be a primitive or a composite. Primitive values are immutable. Primitives are
 * either
 * <ul>
 *     <li>one of the three literals {@link com.mictale.jsonite.JsonValue#NULL},</li>
 *     <li>{@link com.mictale.jsonite.JsonValue#TRUE}</li>
 *     <li>or {@link com.mictale.jsonite.JsonValue#FALSE},</li>
 *     <li>an instance of {@link com.mictale.jsonite.JsonString} or</li>
 *     <li>an instance of {@link com.mictale.jsonite.JsonNumber}.</li>
 * </ul>
 *
 * The two composite types are {@link com.mictale.jsonite.JsonArray} and
 * {@link com.mictale.jsonite.JsonObject}.
 * <p>
 * If you want to stream a JSON graph, have a look at <code>com.mictale.jsonite.stream</code>
 *
 * <h2>Related Documentation</h2>
 *
 * <a href="http://www.ecma-international.org/publications/files/ECMA-ST/Ecma-262.pdf">ECMA-262</a>
 *
 */
package com.mictale.jsonite;

