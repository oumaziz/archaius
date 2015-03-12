package com.netflix.archaius.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;

import com.netflix.archaius.Config;
import com.netflix.archaius.Decoder;
import com.netflix.archaius.DefaultDecoder;
import com.netflix.archaius.StrInterpolator;
import com.netflix.archaius.exceptions.ParseException;
import com.netflix.archaius.interpolate.CommonsStrInterpolatorFactory;

public abstract class AbstractConfig implements Config {

    private final String name;
    private StrInterpolator interpolator;
    private Config parent;
    private Decoder decoder;

    public AbstractConfig(String name) {
        this.name = name;
        this.interpolator = CommonsStrInterpolatorFactory.INSTANCE.create(this);
        this.decoder = new DefaultDecoder();
    }
    
    public void setStrInterpolator(StrInterpolator interpolator) {
        this.interpolator = interpolator;
    }
    
    public StrInterpolator getStrInterpolator() {
        return this.interpolator;
    }

    public Decoder getDecoder() {
        return decoder;
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    public Config getParent() {
        return parent;
    }
    
    public void setParent(Config config) {
        this.parent = config;
    }

    @Override
    public String interpolate(String key) {
        String prop = getRawString(key);
        if (prop == null) {
            return null;    // TODO: Should this thrown an exception?
        }
        return interpolator.resolve(prop.toString());
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getLong(String key) {
        String value = getRawString(key);
        if (value == null) 
            return notFound();
        try {
            return Long.parseLong(value);
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        String value = getRawString(key);
        if (value == null) 
            return defaultValue;
        try {
            return Long.parseLong(value);
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public String getString(String key) {
        Object value = getRawString(key);
        if (value == null) 
            return notFound();
        return value.toString();
    }

    @Override
    public String getString(String key, String defaultValue) {
        String value = getRawString(key);
        if (value == null) 
            return notFound(defaultValue);
        return value;
    }

    @Override
    public Double getDouble(String key) {
        String value = getRawString(key);
        if (value == null) 
            return notFound();
        try {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public Double getDouble(String key, Double defaultValue) {
        String value = getRawString(key);
        if (value == null) 
            return notFound(defaultValue);
        try {   
            return Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public Integer getInteger(String key) {
        String value = getRawString(key);
        if (value == null) 
            return notFound();

        try {   
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public Integer getInteger(String key, Integer defaultValue) {
        String value = getRawString(key);
        if (value == null) 
            return notFound(defaultValue);
        
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public Boolean getBoolean(String key) {
        String value = getRawString(key);
        if (value == null) 
            return notFound();
        
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("on")) {
            return Boolean.TRUE;
        } 
        else if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("no") || value.equalsIgnoreCase("off")) {
            return Boolean.FALSE;
        }
        return parseError(key, value, new Exception("Expected one of [true, yes, on, false, no, off]"));
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        String value = getRawString(key);
        if (value == null) 
            return notFound(defaultValue);
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("on")) {
            return Boolean.TRUE;
        } 
        else if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("no") || value.equalsIgnoreCase("off")) {
            return Boolean.FALSE;
        }
        return parseError(key, value, new Exception("Expected one of [true, yes, on, false, no, off]"));
    }

    @Override
    public Short getShort(String key) {
        String value = getRawString(key);
        if (value == null) 
            return notFound();
        
        try {
            return Short.parseShort(value);
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public Short getShort(String key, Short defaultValue) {
        String value = getRawString(key);
        if (value == null) 
            return notFound(defaultValue);
        try {
            return Short.parseShort(value);
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public BigInteger getBigInteger(String key) {
        String value = getRawString(key);
        if (value == null) 
            return notFound();
        try {
            return BigInteger.valueOf(Long.valueOf(value));
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public BigInteger getBigInteger(String key, BigInteger defaultValue) {
        String value = getRawString(key);
        if (value == null) 
            return notFound(defaultValue);
        try {
            return BigInteger.valueOf(Long.valueOf(value));
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public BigDecimal getBigDecimal(String key) {
        String value = getRawString(key);
        if (value == null) 
            return notFound();
        try {
            return BigDecimal.valueOf(Long.valueOf(value));
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        String value = getRawString(key);
        if (value == null) 
            return notFound(defaultValue);
        try {
            return BigDecimal.valueOf(Long.valueOf(value));
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public Float getFloat(String key) {
        String value = getRawString(key);
        if (value == null) 
            return notFound();
        try {
            return Float.parseFloat(value);
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public Float getFloat(String key, Float defaultValue) {
        String value = getRawString(key);
        if (value == null) 
            return notFound(defaultValue);
        try {
            return Float.parseFloat(value);
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public Byte getByte(String key) {
        String value = getRawString(key);
        if (value == null) 
            return notFound();
        try {
            return Byte.parseByte(value);
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public Byte getByte(String key, Byte defaultValue) {
        String value = getRawString(key);
        if (value == null) 
            return notFound(defaultValue);
        try {
            return Byte.parseByte(value);
        }
        catch (NumberFormatException e) {
            return parseError(key, value, e);
        }
    }

    @Override
    public List getList(String key) {
        String value = getRawString(key);
        if (value == null) {
            return notFound();
        }
        String[] parts = value.split(",");
        return Arrays.asList(parts);
    }

    @Override
    public List getList(String key, List defaultValue) {
        String value = getRawString(key);
        if (value == null) {
            return notFound(defaultValue);
        }
        String[] parts = value.split(",");
        return Arrays.asList(parts);
    }

    @Override
    public <T> T get(Class<T> type, String key) {
        return get(type, key, null);
    }

    @Override
    public <T> T get(Class<T> type, String key, T defaultValue) {
        String value = getRawString(key);
        if (value == null) {
            return notFound(defaultValue);
        }
        return decoder.decode(type, value);
    }

    @Override
    public Iterator<String> getKeys(String prefix) {
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        Iterator<String> keys = getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (key.startsWith(prefix)) {
                result.add(key);
            }
        }
        
        return result.iterator();
    }

    /**
     * Handle notFound when a defaultValue is provided.
     * @param defaultValue
     * @return
     */
    private <T> T notFound(T defaultValue) {
        return defaultValue;
    }
    
    private <T> T notFound() {
        throw new NoSuchElementException();
    }
    
    private <T> T parseError(String key, String value, Exception e) {
        throw new ParseException("Error parsing value '" + value + "' for property '" + key + "'", e);
    }
    
    @Override
    public Config subset(String prefix) {
        return new PrefixedViewConfig(prefix, this);
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public void accept(Visitor visitor) {
        Iterator<String> iter = getKeys();
        while (iter.hasNext()) {
            visitor.visit(this, iter.next());
        }
    }
}
