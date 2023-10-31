package com.tiny.spring.web.http.convert;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: markus
 * @date: 2023/10/31 11:05 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface HttpMessageConverter {
    void write(Object obj, HttpServletResponse response) throws Exception;
}
