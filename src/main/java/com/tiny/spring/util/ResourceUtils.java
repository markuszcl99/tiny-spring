package com.tiny.spring.util;

import java.net.URLConnection;

/**
 * @author: markus
 * @date: 2023/10/25 11:27 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class ResourceUtils {
    public static void useCachesIfNecessary(URLConnection con) {
        con.setUseCaches(con.getClass().getSimpleName().startsWith("JNLP"));
    }
}
