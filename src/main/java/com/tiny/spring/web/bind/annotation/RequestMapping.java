package com.tiny.spring.web.bind.annotation;

import java.lang.annotation.*;

/**
 * @author: markus
 * @date: 2023/10/26 11:31 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String path() default "";
}
