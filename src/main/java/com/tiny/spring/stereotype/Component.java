package com.tiny.spring.stereotype;

import java.lang.annotation.*;

/**
 * @author: markus
 * @date: 2023/10/26 11:29 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";
}
