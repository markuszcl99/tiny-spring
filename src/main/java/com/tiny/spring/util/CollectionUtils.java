package com.tiny.spring.util;

import com.sun.istack.internal.Nullable;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * @author: markus
 * @date: 2023/10/25 11:31 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class CollectionUtils {
    @SuppressWarnings("unchecked")
    public static <K, V> void mergePropertiesIntoMap(@Nullable Properties props, Map<K, V> map) {
        if (props != null) {
            for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements();) {
                String key = (String) en.nextElement();
                Object value = props.get(key);
                if (value == null) {
                    // Allow for defaults fallback or potentially overridden accessor...
                    value = props.getProperty(key);
                }
                map.put((K) key, (V) value);
            }
        }
    }
}
