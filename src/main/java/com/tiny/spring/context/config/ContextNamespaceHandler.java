package com.tiny.spring.context.config;

import com.tiny.spring.beans.factory.xml.NamespaceHandlerSupport;
import com.tiny.spring.context.annotation.ComponentScanBeanDefinitionParser;

/**
 * @author: markus
 * @date: 2023/10/24 12:17 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ContextNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        // 包扫描路径解析器注册
        registerBeanDefinitionParser("component-scan", new ComponentScanBeanDefinitionParser());
    }
}
