package com.tiny.spring.beans.factory.xml;

import com.sun.istack.internal.Nullable;
import com.tiny.spring.beans.factory.ArgumentValue;
import com.tiny.spring.beans.factory.ArgumentValues;
import com.tiny.spring.beans.factory.PropertyValue;
import com.tiny.spring.beans.factory.PropertyValues;
import com.tiny.spring.beans.factory.config.BeanDefinition;
import com.tiny.spring.util.StringUtils;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: markus
 * @date: 2023/10/24 11:18 PM
 * @Description: 解析BeanDefinition
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class BeanDefinitionParserDelegate {

    public static final String BEAN_ELEMENT = "bean";

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    private final XmlReaderContext readerContext;

    public BeanDefinitionParserDelegate(XmlReaderContext readerContext) {
        this.readerContext = readerContext;
    }

    public boolean isDefaultNamespace(Element element) {
        return isDefaultNamespace(getNamespaceURI(element));
    }

    public boolean isDefaultNamespace(@Nullable String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    @Nullable
    public String getNamespaceURI(Element element) {
        return element.getNamespaceURI();
    }

    public BeanDefinition parseBeanDefinitionElement(Element element) {
        String beanId = element.attributeValue("id");
        String className = element.attributeValue("class");
        BeanDefinition beanDefinition = new BeanDefinition(beanId, className);
        // 处理属性
        List<Element> propertyElements = element.elements("property");
        PropertyValues pvs = new PropertyValues();
        // 存储属性中的引用对象 bean的id
        List<String> refs = new ArrayList<>();
        for (Element propertyElement : propertyElements) {
            String pType = propertyElement.attributeValue("type");
            String pName = propertyElement.attributeValue("name");
            String pValue = propertyElement.attributeValue("value");
            String pRef = propertyElement.attributeValue("ref");
            String pV = "";
            boolean isRef = false;
            if (pValue != null && !pValue.equals("")) {
                pV = pValue;
            } else if (pRef != null && !pRef.equals("")) {
                isRef = true;
                pV = pRef;
                refs.add(pRef);
            }
            pvs.addPropertyValue(new PropertyValue(pType, pName, pV, isRef));
        }
        beanDefinition.setPropertyValues(pvs);
        String[] refArray = refs.toArray(new String[0]);
        beanDefinition.setDependsOn(refArray);

        // 处理构造器参数
        List<Element> constructorArgumentElements = element.elements("constructor-arg");
        ArgumentValues avs = new ArgumentValues();
        for (Element constructorArgumentElement : constructorArgumentElements) {
            String aType = constructorArgumentElement.attributeValue("type");
            String aName = constructorArgumentElement.attributeValue("name");
            String aValue = constructorArgumentElement.attributeValue("value");
            avs.addArgumentValue(new ArgumentValue(aValue, aType, aName));
        }
        beanDefinition.setConstructorArgumentValues(avs);
        return beanDefinition;
    }


    /**
     * 判断节点的名称是否相同
     *
     * @param element
     * @param desiredName
     * @return
     */
    public boolean nodeNameEquals(Element element, String desiredName) {
        return desiredName.equals(element.getName());
    }

    public BeanDefinition parseCustomElement(Element element) {
        // 获取标签的命名空间uri
        String namespaceUri = element.getNamespaceURI();
        if (namespaceUri == null) {
            return null;
        }

        // 根据uri获取具体的NamespaceHandler
        NamespaceHandler namespaceHandler = this.readerContext.getNamespaceHandlerResolver().resolve(namespaceUri);
        return namespaceHandler.parse(element, new ParserContext(this.readerContext));
    }
}
