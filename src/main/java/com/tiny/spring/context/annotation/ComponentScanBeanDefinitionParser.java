package com.tiny.spring.context.annotation;

import com.tiny.spring.beans.factory.config.BeanDefinition;
import com.tiny.spring.beans.factory.support.DefaultListableBeanFactory;
import com.tiny.spring.beans.factory.xml.BeanDefinitionParser;
import com.tiny.spring.beans.factory.xml.ParserContext;
import org.dom4j.Element;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author: markus
 * @date: 2023/10/25 10:45 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ComponentScanBeanDefinitionParser implements BeanDefinitionParser {

    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        // 1. 获取扫描路径(简单一点，先仅支持一个包路径，不支持数组)
        String basePackage = element.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        // 2. 扫描包路径，然后生成BeanDefinition，将其注册到BeanFactory中
        List<String> beanNames = scanPackages(Collections.singletonList(basePackage));
        registerBeanDefinitions(beanNames, parserContext);
        // 不需要返回
        return null;
    }

    private void registerBeanDefinitions(List<String> beanNames, ParserContext parserContext) {
        DefaultListableBeanFactory beanFactory = parserContext.getReaderContext().getReader().getBeanFactory();
        for (String beanName : beanNames) {
            String beanId = beanName;
            String className = beanName;
            BeanDefinition beanDefinition = new BeanDefinition(beanId, className);
            beanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }

    private List<String> scanPackages(List<String> packageNames) {
        List<String> tempBeanNames = new ArrayList<>();
        for (String packageName : packageNames) {
            tempBeanNames.addAll(scanPackage(packageName));
        }
        return tempBeanNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> tempBeanNames = new ArrayList<>();
        URI uri = null;
        try {
            uri = this.getClass().getResource("/" + packageName.replaceAll("\\.", "/")).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert uri != null;
        File dir = new File(uri);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                tempBeanNames.addAll(scanPackage(packageName + "." + file.getName()));
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempBeanNames.add(controllerName);
            }
        }
        return tempBeanNames;
    }
}
