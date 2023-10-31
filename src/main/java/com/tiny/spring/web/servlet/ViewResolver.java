package com.tiny.spring.web.servlet;

/**
 * @author: markus
 * @date: 2023/10/31 11:45 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface ViewResolver {
    View resolveViewName(String viewName) throws Exception;
}
