package com.tiny.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: markus
 * @date: 2023/10/28 8:39 PM
 * @Description: 适配器
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface HandlerAdapter {

    void handler(HttpServletRequest request, HttpServletResponse response, Object handler);
}
