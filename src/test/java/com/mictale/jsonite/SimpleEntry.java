package com.mictale.jsonite;

import java.util.Map;

/**
 * Created by ms on 1/2/16.
 */
public class SimpleEntry<K, V> implements Map.Entry<K, V> {

    private final K key;

    private final V value;

    SimpleEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return null;
    }

    @Override
    public V getValue() {
        return null;
    }

    @Override
    public V setValue(V value) {
        return null;
    }
}
