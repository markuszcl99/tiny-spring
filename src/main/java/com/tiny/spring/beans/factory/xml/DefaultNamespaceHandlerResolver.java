package com.tiny.spring.beans.factory.xml;

import java.util.Map;

/**
 * @author: markus
 * @date: 2023/10/24 12:11 AM
 * @Description: 默认的命名空间解析器实现
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DefaultNamespaceHandlerResolver implements NamespaceHandlerResolver {

    public static final String DEFAULT_HANDLER_MAPPING_LOCATION = "META-INF/spring.handlers";

    private final String handlerMappingsLocations;
    private volatile Map<String, Object> handlerMappings;

    public DefaultNamespaceHandlerResolver() {
        this.handlerMappingsLocations = DEFAULT_HANDLER_MAPPING_LOCATION;
    }


    @Override
    public NamespaceHandler resolve(String namespaceUri) {
        return (NamespaceHandler) handlerMappings.get(namespaceUri);
    }
}
