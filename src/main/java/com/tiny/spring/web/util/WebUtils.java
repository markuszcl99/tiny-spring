package com.tiny.spring.web.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author: markus
 * @date: 2023/10/29 7:03 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class WebUtils {

    public static Map<String, Object> getParametersStartingWith(HttpServletRequest request, String prefix) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<>();
        if (prefix == null) {
            prefix = "";
        }

        while (parameterNames != null && parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (prefix.isEmpty() || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String value = request.getParameter(paramName);
                params.put(unprefixed, value);
            }
        }
        return params;
    }
}
