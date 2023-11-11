package com.tiny.spring.beans.factory;

import com.sun.istack.internal.Nullable;

/**
 * @author: markus
 * @date: 2023/11/11 10:42 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class BeanFactoryUtils {

    public static boolean isFactoryDereference(@Nullable String name) {
        return (name != null && name.startsWith(BeanFactory.FACTORY_BEAN_PREFIX));
    }
}
