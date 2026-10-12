package com.mictale.jsonite;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/** JsonArray as a java.util.List, its cursor, and its equality. */
public class JsonArrayListTest {

    private static JsonArray ints(int... values) {
        JsonArray a = new JsonArray();
        for (int v : values) a.add(JsonValue.of(v));
        return a;
    }

    @Test
    public void equalityIsOrdered() {
        assertThat(ints(1, 2), not(equalTo(ints(2, 1))));
    }

    @Test
    public void equalityCountsDuplicates() {
        assertThat(ints(1, 1, 2), not(equalTo(ints(1, 2, 2))));
    }

    @Test
    public void equalArraysHaveEqualHashCodes() {
        assertThat(ints(1, 2, 3), equalTo(ints(1, 2, 3)));
        assertThat(ints(1, 2, 3).hashCode(), equalTo(ints(1, 2, 3).hashCode()));
        Set<JsonValue> set = new HashSet<>();
        set.add(ints(1, 2));
        assertThat(set.contains(ints(1, 2)), equalTo(true));
    }

    @Test
    public void arrayIsNotEqualToOtherTypes() {
        assertThat(ints(1).equals(JsonValue.of(1)), equalTo(false));
    }

    @Test
    public void factoriesConvertElements() {
        assertThat(JsonArray.of(JsonValue.of(1)), equalTo(ints(1)));
        assertThat(JsonArray.of(JsonValue.of(1), JsonValue.of(2)), equalTo(ints(1, 2)));
        assertThat(JsonArray.of(1, 2), equalTo(ints(1, 2)));
        assertThat(JsonArray.of(Arrays.asList(1, 2)), equalTo(ints(1, 2)));
    }

    @Test
    public void cursorWalksAndRewinds() {
        JsonArray a = ints(5, 6);
        assertThat(a.hasNext(), equalTo(true));
        assertThat(a.next().intValue(), equalTo(5));
        assertThat(a.position(), equalTo(1));
        assertThat(a.next().intValue(), equalTo(6));
        assertThat(a.hasNext(), equalTo(false));
        a.rewind();
        assertThat(a.position(), equalTo(0));
        assertThat(a.next().intValue(), equalTo(5));
    }

    @Test
    public void listOperations() {
        JsonArray a = ints(1, 2, 3);
        a.push(JsonValue.of(4));
        assertThat(a.length(), equalTo(4));
        a.add(0, JsonValue.of(0));
        assertThat(a.get(0).intValue(), equalTo(0));
        assertThat(a.indexOf(JsonValue.of(2)), equalTo(2));
        assertThat(a.contains(JsonValue.of(3)), equalTo(true));
        assertThat(a.containsAll(ints(1, 4)), equalTo(true));
        assertThat(a.set(0, JsonValue.of(9)).intValue(), equalTo(0));
        assertThat(a.remove(0).intValue(), equalTo(9));
        assertThat(a.remove(JsonValue.of(4)), equalTo(true));
        assertThat(a, equalTo(ints(1, 2, 3)));
        a.addAll(ints(2));
        assertThat(a.lastIndexOf(JsonValue.of(2)), equalTo(3));
        a.addAll(0, ints(7));
        assertThat(a.subList(0, 2), equalTo((List<JsonValue>) ints(7, 1)));
        a.removeAll(ints(2));
        assertThat(a, equalTo(ints(7, 1, 3)));
        a.retainAll(ints(1, 3));
        assertThat(a, equalTo(ints(1, 3)));
        assertThat(a.toArray().length, equalTo(2));
        assertThat(a.toArray(new JsonValue[0]).length, equalTo(2));
        a.clear();
        assertThat(a.isEmpty(), equalTo(true));
    }

    @Test
    public void iteratorRemovesTheCurrentElement() {
        JsonArray a = ints(1, 2, 3);
        Iterator<JsonValue> it = a.iterator();
        while (it.hasNext()) {
            if (it.next().intValue() == 2) it.remove();
        }
        assertThat(a, equalTo(ints(1, 3)));
    }

    @Test
    public void listIteratorStartsAtTheIndex() {
        ListIterator<JsonValue> it = ints(1, 2, 3).listIterator(1);
        assertThat(it.next().intValue(), equalTo(2));
        assertThat(ints(1).listIterator().next().intValue(), equalTo(1));
    }

    @Test
    public void typeAndObjectValue() {
        JsonArray a = ints(1);
        assertThat(a.isArray(), equalTo(true));
        assertThat(a.asArray() == a, equalTo(true));
        assertThat(a.getType(), equalTo(JsonType.ARRAY));
        assertThat(((List<?>) a.objectValue()).size(), equalTo(1));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void emptyIsImmutable() {
        JsonArray.EMPTY.add(JsonValue.of(1));
    }
}
