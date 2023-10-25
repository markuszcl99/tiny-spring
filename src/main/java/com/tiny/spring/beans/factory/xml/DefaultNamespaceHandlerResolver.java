package com.tiny.spring.beans.factory.xml;

import com.sun.istack.internal.Nullable;
import com.tiny.spring.core.io.support.PropertiesLoaderUtils;
import com.tiny.spring.util.ClassUtils;
import com.tiny.spring.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: markus
 * @date: 2023/10/24 12:11 AM
 * @Description: 默认的命名空间解析器实现
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DefaultNamespaceHandlerResolver implements NamespaceHandlerResolver {

    public static final String DEFAULT_HANDLER_MAPPING_LOCATION = "META-INF/spring.handlers";

    private final String handlerMappingsLocation;
    private volatile Map<String, Object> handlerMappings;

    public DefaultNamespaceHandlerResolver() {
        this.handlerMappingsLocation = DEFAULT_HANDLER_MAPPING_LOCATION;

    }


    @Override
    @Nullable
    public NamespaceHandler resolve(String namespaceUri) {
        Map<String, Object> handlerMappings = getHandlerMappings();
        Object handlerOrClassName = handlerMappings.get(namespaceUri);
        if (handlerOrClassName == null) {
            return null;
        }
        if (handlerOrClassName instanceof NamespaceHandler) {
            return (NamespaceHandler) handlerOrClassName;
        } else {
            String className = (String) handlerOrClassName;
            try {
                Class<?> classForHandler = Class.forName(className);
                NamespaceHandler namespaceHandler = (NamespaceHandler) classForHandler.newInstance();
                // 初始化
                namespaceHandler.init();
                handlerMappings.put(namespaceUri, namespaceHandler);
                return namespaceHandler;
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Map<String, Object> getHandlerMappings() {
        Map<String, Object> handlerMappings = this.handlerMappings;
        if (handlerMappings == null) {
            try {
                Properties properties = PropertiesLoaderUtils.readConfigFile(handlerMappingsLocation, null);
                handlerMappings = new ConcurrentHashMap<>(properties.size());
                CollectionUtils.mergePropertiesIntoMap(properties, handlerMappings);
                this.handlerMappings = handlerMappings;
            } catch (IOException ex) {
                throw new IllegalStateException(
                        "Unable to load NamespaceHandler mappings from location [" + this.handlerMappingsLocation + "]", ex);
            }
        }
        return handlerMappings;
    }
}
