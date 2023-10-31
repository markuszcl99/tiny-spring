package com.tiny.spring.web.http.convert;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author: markus
 * @date: 2023/10/31 11:08 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DefaultHttpMessageConverter implements HttpMessageConverter {

    private String defaultContentType = "text/json;charset=UTF-8";
    private String defaultCharacterEncoding = "UTF-8";

    public ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void write(Object obj, HttpServletResponse response) throws Exception {
        response.setContentType(defaultContentType);
        response.setCharacterEncoding(defaultCharacterEncoding);
        writeInternal(obj, response);
        response.flushBuffer();
    }

    private void writeInternal(Object obj, HttpServletResponse response) throws Exception {
        String jsonStr = this.objectMapper.writeValuesAsString(obj);
        PrintWriter pw = response.getWriter();
        pw.write(jsonStr);
    }
}
