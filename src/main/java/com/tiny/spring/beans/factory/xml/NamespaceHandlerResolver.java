package com.tiny.spring.beans.factory.xml;

/**
 * @author: markus
 * @date: 2023/10/24 12:01 AM
 * @Description: 命名空间解析器-用于xml中自定义的命名空间标签解析
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@FunctionalInterface
public interface NamespaceHandlerResolver {
    /**
     * 通过命名空间的uri获取到具体的NamespaceHandler
     * @param namespaceUri
     * @return
     */
    NamespaceHandler resolve(String namespaceUri);
}
