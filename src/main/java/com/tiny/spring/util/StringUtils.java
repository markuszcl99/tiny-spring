package com.tiny.spring.util;

/**
 * @author: markus
 * @date: 2023/10/24 11:40 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class StringUtils {

    public static boolean hasLength(String text) {
        return text != null && !text.isEmpty();
    }
}
