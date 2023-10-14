package com.tiny.spring.core.env;

/**
 * @author: markus
 * @date: 2023/10/15 12:08 AM
 * @Description: 属性解析器
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface PropertyResolver {
    boolean containsProperty(String key);

    String getProperty(String key);

    String getProperty(String key, String defaultValue);

    <T> T getProperty(String key, Class<T> targetType);

    <T> T getProperty(String key, Class<T> targetType, T defaultValue);

    <T> Class<T> getPropertyAsClass(String key, Class<T> targetClass);

    String getRequiredProperty(String key) throws IllegalStateException;

    <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException;

    String resolvePlaceholders(String text);

    String resoleRequiredPlaceholders(String text) throws IllegalStateException;
}
