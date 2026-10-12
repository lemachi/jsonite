package com.mictale.jsonite;

import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

/** JsonValue.of for Java objects, and typed access that must refuse the wrong type. */
public class ConversionTest {

    @Test
    public void ofConvertsJavaObjects() {
        assertThat(JsonValue.of(null), equalTo(JsonValue.NULL));
        assertThat(JsonValue.of('x').stringValue(), equalTo("x"));
        assertThat(JsonValue.of(new Date(1234L)).longValue(), equalTo(1234L));
        assertThat(JsonValue.of(Arrays.asList(1, "a")), equalTo((JsonValue) JsonArray.of(1, "a")));
        assertThat(JsonValue.of(true), equalTo(JsonValue.TRUE));
    }

    @Test
    public void ofPassesJsonValuesThrough() {
        JsonValue v = JsonValue.of(5);
        assertThat(JsonValue.of((Object) v) == v, equalTo(true));
    }

    @Test
    public void ofAsksHasJson() {
        HasJson h = () -> JsonValue.of("from HasJson");
        assertThat(JsonValue.of(h).stringValue(), equalTo("from HasJson"));
    }

    @Test
    public void ofRefusesUnknownTypes() {
        JsonException e = assertThrows(JsonException.class, () -> JsonValue.of(new Object()));
        assertThat(e.getMessage(), containsString("java.lang.Object"));
    }

    @Test
    public void numbersPickTheNarrowestType() {
        assertThat(JsonNumber.of(3L).objectValue(), instanceOf(Integer.class));
        assertThat(JsonNumber.of(1L << 40).objectValue(), instanceOf(Long.class));
        assertThat(JsonNumber.of(2.0).objectValue(), instanceOf(Integer.class));
        assertThat(JsonNumber.of(2.5).objectValue(), instanceOf(Double.class));
        assertThat(JsonNumber.of((Number) null), equalTo(JsonValue.NULL));
    }

    @Test
    public void numberAccessors() {
        JsonValue n = JsonValue.of(300);
        assertThat(n.isNumber(), equalTo(true));
        assertThat(n.asNumber() == n, equalTo(true));
        assertThat(n.isPrimitive(), equalTo(true));
        assertThat(n.byteValue(), equalTo((byte) 44));
        assertThat(n.shortValue(), equalTo((short) 300));
        assertThat(n.longValue(), equalTo(300L));
        assertThat(n.floatValue(), equalTo(300f));
        assertThat(n.stringValue(), equalTo("300"));
    }

    @Test
    public void wrongTypeAccessIsRefusedWithBothTypesNamed() {
        JsonValue s = JsonValue.of("text");
        JsonConversionException e = assertThrows(JsonConversionException.class, s::intValue);
        assertThat(e.getMessage(), containsString("String"));
        assertThat(e.getMessage(), containsString("int"));
        assertThrows(JsonConversionException.class, s::booleanValue);
        assertThrows(JsonConversionException.class, s::byteValue);
        assertThrows(JsonConversionException.class, s::shortValue);
        assertThrows(JsonConversionException.class, s::longValue);
        assertThrows(JsonConversionException.class, s::floatValue);
        assertThrows(JsonConversionException.class, s::doubleValue);
        assertThrows(JsonConversionException.class, s::asNumber);
        assertThrows(JsonConversionException.class, s::asBoolean);
        assertThrows(JsonConversionException.class, () -> JsonValue.of(1).booleanValue());
        // Numbers answer stringValue: the writer relies on it.
        assertThat(JsonValue.of(1).stringValue(), equalTo("1"));
    }

    @Test
    public void objectValueOfAContainerTypeNamesObject() {
        JsonValue v = new JsonObject();
        // JsonObject answers objectValue; the default refuses with the right name.
        assertThat(v.objectValue(), instanceOf(java.util.Map.class));
        JsonConversionException e = assertThrows(JsonConversionException.class, () -> new JsonValue() {
            @Override public JsonType getType() { return JsonType.OBJECT; }
            @Override public void accept(com.mictale.jsonite.stream.JsonVisitor visitor) { }
        }.objectValue());
        assertThat(e.getMessage(), containsString("Object"));
    }

    @Test
    public void typePredicatesAreExclusive() {
        JsonValue s = JsonValue.of("x");
        assertThat(s.isString(), equalTo(true));
        assertThat(s.isNumber() || s.isBoolean() || s.isNull() || s.isObject() || s.isArray(), equalTo(false));
    }

    @Test
    public void nullBehavesAsAbsentValue() {
        JsonValue n = JsonValue.NULL;
        assertThat(n.isNull(), equalTo(true));
        assertThat(n.stringValue(), nullValue());
        assertThat(n.objectValue(), nullValue());
        assertThat(Double.isNaN(n.doubleValue()), equalTo(true));
        assertThat(Float.isNaN(n.floatValue()), equalTo(true));
        assertThat(n.hashCode(), equalTo(JsonValue.NULL.hashCode()));
    }

    @Test
    public void ofNullWrappersIsNull() {
        assertThat(JsonString.of(null), equalTo(JsonValue.NULL));
        assertThat(JsonBoolean.of(null), equalTo(JsonValue.NULL));
    }

    @Test
    public void stringAndBooleanEquality() {
        assertThat(JsonValue.of("a"), equalTo(JsonValue.of("a")));
        assertThat(JsonValue.of("a").hashCode(), equalTo(JsonValue.of("a").hashCode()));
        assertThat(JsonValue.of(true).hashCode(), equalTo(JsonValue.TRUE.hashCode()));
        assertThat(JsonValue.TRUE.equals(JsonValue.FALSE), equalTo(false));
        assertThat(JsonValue.TRUE.booleanValue(), equalTo(true));
        assertThat(JsonValue.TRUE.objectValue(), equalTo((Object) Boolean.TRUE));
        assertThat(JsonValue.of("a").objectValue(), equalTo((Object) "a"));
    }

    @Test
    public void staticEqualsHandlesNulls() {
        assertThat(JsonValue.equals(null, null), equalTo(true));
        assertThat(JsonValue.equals(JsonValue.of(1), null), equalTo(false));
        assertThat(JsonValue.equals(null, JsonValue.of(1)), equalTo(false));
        assertThat(JsonValue.equals(JsonValue.of(1), JsonValue.of(1)), equalTo(true));
    }

    @Test
    public void objectMapOperations() {
        JsonObject o = new JsonObject();
        o.put("a", (JsonValue) null);
        assertThat(o.get("a").isNull(), equalTo(true));
        assertThat(o.get("missing").isNull(), equalTo(true));
        assertThat(o.containsValue(JsonValue.NULL), equalTo(true));
        o.putAll(JsonValue.parse("{\"b\":1,\"c\":2}").asObject());
        assertThat(o.keySet().size(), equalTo(3));
        assertThat(o.values().size(), equalTo(3));
        assertThat(o.isEmpty(), equalTo(false));
        assertThat(o.equals(JsonValue.of(1)), equalTo(false));
    }
}
