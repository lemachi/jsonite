package com.mictale.jsonite.stream;

import com.mictale.jsonite.JsonValue;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/** Every string the writer produces is valid JSON and reads back unchanged. */
public class WriterEscapingTest {

    private static String write(String s) {
        return JsonValue.of(s).toString();
    }

    @Test
    public void namedEscapes() {
        assertThat(write("\"\\/"), equalTo("\"\\\"\\\\\\/\""));
        assertThat(write("\b\f\n\r\t"), equalTo("\"\\b\\f\\n\\r\\t\""));
    }

    @Test
    public void otherControlCharactersUseUnicodeEscapes() {
        assertThat(write("\u0001\u001f"), equalTo("\"\\u0001\\u001f\""));
    }

    @Test
    public void nonAsciiIsWrittenAsIs() {
        assertThat(write("\u00e9\u20ac\ud83d\ude00"), equalTo("\"\u00e9\u20ac\ud83d\ude00\""));
    }

    @Test
    public void everyCharacterRoundTrips() {
        StringBuilder sb = new StringBuilder();
        for (char c = 0; c < 0x300; c++) sb.append(c);
        sb.append("\u20ac\ud83d\ude00\uffff");
        String s = sb.toString();
        String json = write(s);
        for (int i = 0; i < json.length(); i++) {
            assertThat("raw control character at " + i, json.charAt(i) >= 0x20, equalTo(true));
        }
        assertThat(JsonValue.parse(json).stringValue(), equalTo(s));
    }

    @Test
    public void booleansAndNull() {
        assertThat(JsonValue.TRUE.toString(), equalTo("true"));
        assertThat(JsonValue.FALSE.toString(), equalTo("false"));
        assertThat(JsonValue.NULL.toString(), equalTo("null"));
    }
}
