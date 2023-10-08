package com.tiny.spring.beans.factory.support;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.*;
import com.tiny.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: markus
 * @date: 2023/10/7 8:52 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    /* bean definition map*/
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private List<String> beanDefinitionNames = new ArrayList<>();

    public SimpleBeanFactory() {
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿bean实例
        Object singleton = getSingleton(beanName);
        if (singleton == null) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition == null) {
                throw new BeansException("The bean name does not exit. bean name [" + beanName + "]");
            } else {
                // 获取Bean的定义
                try {
                    singleton = createBean(beanDefinition);
                } catch (BeansException e) {
                    e.printStackTrace();
                }
                // 注册Bean实例
                this.registerSingleton(beanDefinition.getId(), singleton);
            }
        }
        return singleton;
    }

    @Override
    public boolean containsBean(String beanName) {
        return containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) {
        // todo 这里可能会抛空指针
        return getBeanDefinition(beanName).isSingleton();
    }

    @Override
    public boolean isPrototype(String beanName) {
        // todo 这里可能会抛空指针
        return getBeanDefinition(beanName).isPrototype();
    }

    @Override
    public Class<?> getType(String beanName) {
        // todo 这里可能会抛空指针
        return getBeanDefinition(beanName).getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.beanDefinitionMap.put(name, bd);
        this.beanDefinitionNames.add(name);
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    private Object createBean(BeanDefinition beanDefinition) throws BeansException {
        Class<?> clazz = null;
        Object obj = null;
        Constructor<?> constructor = null;

        try {
            clazz = Class.forName(beanDefinition.getClassName());
            // 处理构造器参数
            ArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
            // 如果有参数
            if (!argumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
                Object[] paramValues = new Object[argumentValues.getArgumentCount()];
                // 对每一个参数，分数据类型分别处理
                for (int i = 0; i < argumentValues.getArgumentCount(); i++) {
                    ArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);
                    if ("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    } else if ("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                    } else if ("int".equals(argumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                    } else { //默认为string
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    }
                }
                // 按照特定构造器创建实例
                constructor = clazz.getConstructor(paramTypes);
                obj = constructor.newInstance(paramValues);
            } else {
                // 如果没有构造器参数，则直接使用默认构造器创建实例即可
                obj = clazz.newInstance();
            }
        } catch (Exception e) {
            throw new BeansException("bean constructor invoke error,bean id is" + beanDefinition.getId());
        }

        // 创建实例后，进行属性赋值
        PropertyValues pvs = beanDefinition.getPropertyValues();
        if (!pvs.isEmpty()) {
            for (int i = 0; i < pvs.getPropertyValues().length; i++) {
                // 对每一个属性，分数据类型分别处理
                PropertyValue propertyValue = pvs.getPropertyValues()[i];
                String pType = propertyValue.getType();
                String pName = propertyValue.getName();
                Object pValue = propertyValue.getValue();
                Class[] paramTypes = new Class[1];
                Object[] paramValues = new Object[1];
                if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                    paramTypes[0] = String.class;
                    paramValues[0] = pValue;
                } else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                    paramTypes[0] = Integer.class;
                    paramValues[0] = Integer.valueOf((String) pValue);
                } else if ("int".equals(pType)) {
                    paramTypes[0] = int.class;
                    paramValues[0] = Integer.valueOf((String) pValue);
                } else { // 默认为string
                    paramTypes[0] = String.class;
                    paramValues[0] = pValue;
                }

                // 按照setXxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                Method method = null;
                try {
                    method = clazz.getMethod(methodName, paramTypes);
                    method.invoke(obj, paramValues);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new BeansException("bean setter method invoke error,bean id is " + beanDefinition.getId() + ",method is " + methodName);
                }

            }
        }
        return obj;
    }
}
