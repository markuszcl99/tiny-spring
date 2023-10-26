package com.tiny.spring.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * @author: markus
 * @date: 2023/10/26 11:23 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class AnnotatedElementUtils {
    public static boolean hasAnnotation(AnnotatedElement element, Class<? extends Annotation> annotationType) {
        return element.isAnnotationPresent(annotationType);
    }
}
