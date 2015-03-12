package com.netflix.archaius.config;

import java.util.Collections;
import java.util.Iterator;

public class EmptyConfig extends AbstractConfig {

    public EmptyConfig(String name) {
        super(name);
    }

    @Override
    public boolean containsProperty(String key) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Iterator<String> getKeys() {
        return Collections.emptyIterator();
    }

    @Override
    public String getRawString(String key) {
        return null;
    }

}
