package com.tiny.spring.beans.factory.config;

import com.tiny.spring.beans.factory.ArgumentValues;
import com.tiny.spring.beans.factory.PropertyValues;

/**
 * @author: markus
 * @date: 2023/10/7 8:14 PM
 * @Description: Bean配置元信息
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class BeanDefinition {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    /**
     * 是否是懒加载
     */
    private boolean lazyInit = false;
    /**
     *
     */
    private String[] dependsOn;
    private ArgumentValues constructorArgumentValues;
    private PropertyValues propertyValues;
    private String initMethodName;
    private volatile Class<?> beanClass;
    private String id;
    private String className;
    private String scope = SCOPE_SINGLETON;

    public BeanDefinition() {
        constructorArgumentValues = new ArgumentValues();
        propertyValues = new PropertyValues();
    }

    public BeanDefinition(String id, String className) {
        this();
        this.id = id;
        this.className = className;
        try {
            Class<?> clazz = Class.forName(className);
            setBeanClass(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String[] getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String[] dependsOn) {
        this.dependsOn = dependsOn;
    }

    public ArgumentValues getConstructorArgumentValues() {
        return constructorArgumentValues;
    }

    public void setConstructorArgumentValues(ArgumentValues constructorArgumentValues) {
        this.constructorArgumentValues = constructorArgumentValues;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isSingleton() {
        return this.scope.equals(SCOPE_SINGLETON);
    }

    public boolean isPrototype() {
        return this.scope.equals(SCOPE_PROTOTYPE);
    }
}
