package com.tiny.spring.web.method;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: markus
 * @date: 2023/10/28 8:50 PM
 * @Description: 映射注册
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class MappingRegistry {
    private List<String> urlMappingNames = new ArrayList<>();
    private Map<String, Object> mappingObjs = new HashMap<>();
    private Map<String, Method> mappingMethods = new HashMap<>();

    public List<String> getUrlMappingNames() {
        return urlMappingNames;
    }

    public void setUrlMappingNames(List<String> urlMappingNames) {
        this.urlMappingNames = urlMappingNames;
    }

    public Map<String, Object> getMappingObjs() {
        return mappingObjs;
    }

    public void setMappingObjs(Map<String, Object> mappingObjs) {
        this.mappingObjs = mappingObjs;
    }

    public Map<String, Method> getMappingMethods() {
        return mappingMethods;
    }

    public void setMappingMethods(Map<String, Method> mappingMethods) {
        this.mappingMethods = mappingMethods;
    }

    public void register(String url, Object obj, Method method) {
        this.urlMappingNames.add(url);
        this.mappingMethods.put(url, method);
        this.mappingObjs.put(url, obj);
    }
}
