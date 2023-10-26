package com.tiny.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: markus
 * @date: 2023/10/26 10:58 PM
 * @Description: 获取处理器
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface HandlerMapping {
    HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;
}
