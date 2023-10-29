package com.tiny.spring.web.bind;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: markus
 * @date: 2023/10/29 11:47 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class WebDataBinderFactory {
    public WebDataBinder createBinder(HttpServletRequest request, Object target, String objectName) {
        WebDataBinder wbd = new WebDataBinder(target, objectName);
        initBinder(wbd, request);
        return wbd;
    }

    protected void initBinder(WebDataBinder wbd, HttpServletRequest request) {

    }
}
