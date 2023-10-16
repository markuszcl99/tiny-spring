package com.tiny.spring.web;

import com.tiny.spring.core.io.ClassPathXmlResource;
import com.tiny.spring.core.io.Resource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: markus
 * @date: 2023/10/16 11:39 PM
 * @Description: 扫描配置@RequestMapping标注的方法，将其类进行注册
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class XmlScanComponentHelper {
    public static List<String> getNodeValue(URL xmlPath) {
        List<String> packages = new ArrayList<>();
        Resource resource = new ClassPathXmlResource(xmlPath);
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            packages.add(element.attributeValue("base-package"));
        }
        return packages;
    }
}
