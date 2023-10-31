package com.tiny.spring.web.bind.annotation;

import java.lang.annotation.*;

/**
 * @author: markus
 * @date: 2023/10/31 11:35 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseBody {
}
