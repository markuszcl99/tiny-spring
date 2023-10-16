package com.tiny.spring.web;

import com.tiny.spring.core.io.Resource;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: markus
 * @date: 2023/10/16 10:47 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class XmlConfigReader {
    public XmlConfigReader() {
    }

    public Map<String, MappingValue> loadConfig(Resource resource) {
        Map<String, MappingValue> mappings = new HashMap<>();
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            String beanMethod = element.attributeValue("value");
            mappings.put(beanId, new MappingValue(beanId, beanClassName, beanMethod));
        }
        return mappings;
    }
}
