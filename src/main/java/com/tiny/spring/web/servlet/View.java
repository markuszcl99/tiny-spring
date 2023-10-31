package com.tiny.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: markus
 * @date: 2023/10/31 11:27 PM
 * @Description: 负责前端展示的组件
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface View {
    /**
     * 核心方法，将数据渲染到页面上
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 获取内容类型
     * @return
     */
    default String getContentType() {
        return null;
    }

    /**
     * 设置内容类型
     * @param contentType
     */
    void setContentType(String contentType);

    /**
     * 设置url
     * @param url
     */
    void setUrl(String url);

    /**
     * 获取url
     * @return
     */
    String getUrl();

    /**
     * 设置请求上下文属性
     * @param requestContextAttribute
     */
    void setRequestContextAttribute(String requestContextAttribute);

    /**
     * 获取请求上下文属性
     * @return
     */
    String getRequestContextAttribute();
}
