package com.tiny.spring.web.servlet.view;

import com.tiny.spring.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: markus
 * @date: 2023/11/1 10:14 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class JstlView implements View {

    public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=ISO-8859-1";
    private String contentType = DEFAULT_CONTENT_TYPE;
    private String requestContextAttribute;
    private String beanName;
    private String url;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置Model数据
        for (Map.Entry<String, ?> modelEntry : model.entrySet()) {
            request.setAttribute(modelEntry.getKey(), modelEntry.getValue());
        }
        // 将请求转发到指定的url
        request.getRequestDispatcher(getUrl()).forward(request, response);
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public void setRequestContextAttribute(String requestContextAttribute) {
        this.requestContextAttribute = requestContextAttribute;
    }

    @Override
    public String getRequestContextAttribute() {
        return this.requestContextAttribute;
    }
}
