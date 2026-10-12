package com.mictale.jsonite.stream;

import com.mictale.jsonite.JsonArray;
import com.mictale.jsonite.JsonException;
import com.mictale.jsonite.JsonObject;
import com.mictale.jsonite.JsonValue;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

/** What the parser accepts, what it produces, and how it rejects bad input. */
public class ParserTest {

    // Numbers

    @Test
    public void integersStayIntegral() {
        assertThat(JsonValue.parse("42").intValue(), equalTo(42));
        assertThat(JsonValue.parse("-7").intValue(), equalTo(-7));
        assertThat(JsonValue.parse("0").intValue(), equalTo(0));
    }

    @Test
    public void longsKeepTheirPrecision() {
        assertThat(JsonValue.parse("9007199254740993").longValue(), equalTo(9007199254740993L));
    }

    @Test
    public void decimals() {
        assertThat(JsonValue.parse("3.25").doubleValue(), equalTo(3.25));
        assertThat(JsonValue.parse("-0.5").doubleValue(), equalTo(-0.5));
    }

    @Test
    public void exponents() {
        assertThat(JsonValue.parse("1e3").doubleValue(), equalTo(1000.0));
        assertThat(JsonValue.parse("1E3").doubleValue(), equalTo(1000.0));
        assertThat(JsonValue.parse("2.5e-2").doubleValue(), equalTo(0.025));
        assertThat(JsonValue.parse("2.5E+2").doubleValue(), equalTo(250.0));
    }

    @Test
    public void numbersInsideContainers() {
        JsonArray a = JsonValue.parse("[1.5, -2, 3e1]").asArray();
        assertThat(a.get(0).doubleValue(), equalTo(1.5));
        assertThat(a.get(1).intValue(), equalTo(-2));
        assertThat(a.get(2).doubleValue(), equalTo(30.0));
    }

    @Test
    public void nanLiteralIsReadBack() {
        // Not JSON, but what earlier versions of the app may have written.
        assertThat(Double.isNaN(JsonValue.parse("NaN").doubleValue()), equalTo(true));
    }

    // Strings

    @Test
    public void simpleEscapes() {
        assertThat(JsonValue.parse("\"a\\\"b\\\\c\\/d\"").stringValue(), equalTo("a\"b\\c/d"));
        assertThat(JsonValue.parse("\"\\b\\f\\n\\r\\t\"").stringValue(), equalTo("\b\f\n\r\t"));
    }

    @Test
    public void whitespaceInsideStringsIsKept() {
        assertThat(JsonValue.parse("\"  a \\n b  \"").stringValue(), equalTo("  a \n b  "));
    }

    @Test
    public void unicodeEscapes() {
        assertThat(JsonValue.parse("\"\\u00e9\\u20AC\\u0041\"").stringValue(), equalTo("\u00e9\u20ac\u0041"));
    }

    @Test
    public void surrogatePairEscapes() {
        assertThat(JsonValue.parse("\"\\ud83d\\ude00\"").stringValue(), equalTo("\ud83d\ude00"));
    }

    @Test
    public void unknownEscapeIsRejected() {
        assertThrows(BrokenStreamException.class, () -> JsonValue.parse("\"a\\xb\""));
    }

    @Test
    public void malformedUnicodeEscapeIsRejected() {
        assertThrows(BrokenStreamException.class, () -> JsonValue.parse("\"\\u12g4\""));
    }

    @Test(timeout = 2000)
    public void unterminatedStringIsRejected() {
        assertThrows(BrokenStreamException.class, () -> JsonValue.parse("\"abc"));
    }

    @Test(timeout = 2000)
    public void unterminatedEscapeIsRejected() {
        assertThrows(BrokenStreamException.class, () -> JsonValue.parse("\"abc\\"));
    }

    // Structure and errors

    @Test
    public void whitespaceBetweenTokens() {
        JsonObject o = JsonValue.parse(" {\r\n\t\"a\" :\n [ true , false , null ] } ").asObject();
        assertThat(o.get("a").asArray().size(), equalTo(3));
        assertThat(o.get("a").asArray().get(2).isNull(), equalTo(true));
    }

    @Test
    public void missingColonIsRejected() {
        assertThrows(JsonException.class, () -> JsonValue.parse("{\"a\" 1}"));
    }

    @Test
    public void signWithoutDigitsIsRejected() {
        BrokenStreamException e = assertThrows(BrokenStreamException.class, () -> JsonValue.parse("-x"));
        assertThat(e.getMessage(), containsString("digit"));
    }

    @Test
    public void exponentWithoutDigitsIsRejected() {
        assertThrows(BrokenStreamException.class, () -> JsonValue.parse("1e"));
        assertThrows(BrokenStreamException.class, () -> JsonValue.parse("[1e+]"));
    }

    @Test
    public void misspelledLiteralReportsLineAndPosition() {
        BrokenStreamException e = assertThrows(BrokenStreamException.class, () -> JsonValue.parse("[\n  tru ]"));
        assertThat(e.getMessage(), containsString("true"));
        assertThat(e.getMessage(), containsString("line 1"));
    }

    @Test
    public void emptyInputIsNull() {
        assertThat(JsonValue.parse("").isNull(), equalTo(true));
        assertThat(JsonValue.parse((String) null).isNull(), equalTo(true));
    }
}
