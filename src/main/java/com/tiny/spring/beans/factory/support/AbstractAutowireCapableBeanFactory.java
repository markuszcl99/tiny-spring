package com.tiny.spring.beans.factory.support;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.*;
import com.tiny.spring.beans.factory.config.AutowireCapableBeanFactory;
import com.tiny.spring.beans.factory.config.BeanDefinition;
import com.tiny.spring.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: markus
 * @date: 2023/10/13 11:29 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Class<?> clazz = null;
        // 创建实例
        Object obj = doCreateBean(beanName, beanDefinition);
        // 将创建好的实例 存储到早期Bean引用缓存中
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);
        try {
            clazz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 初始化Bean实例
        // 处理属性
        Object exposedObject = obj;
        try {
            populateBean(beanDefinition, clazz, exposedObject);
            exposedObject = initializingBean(beanName, exposedObject, beanDefinition);
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName);
        }
        return exposedObject;
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
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
        return obj;
    }

    protected void populateBean(BeanDefinition bd, Class<?> clazz, Object obj) throws BeansException {
        // 处理属性
        System.out.println("handle properties for bean : " + bd.getId());
        // 如果有属性值的话，进行属性赋值
        PropertyValues pvs = bd.getPropertyValues();
        if (!pvs.isEmpty()) {
            for (int i = 0; i < pvs.getPropertyValues().length; i++) {
                // 对每一个属性，分数据类型分别处理
                PropertyValue propertyValue = pvs.getPropertyValues()[i];
                String pType = propertyValue.getType();
                String pName = propertyValue.getName();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.isRef();

                Class[] paramTypes = new Class[1];
                Object[] paramValues = new Object[1];
                if (!isRef) {
                    // 如果不是 ref，只是普通属性，则对该属性分数据类型处理
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
                } else {
                    // 是 ref，则通过 getBean()方式获取引用Bean实例
                    try {
                        paramTypes[0] = Class.forName(pType);
                        paramValues[0] = getBean((String) pValue);
                    } catch (ClassNotFoundException e) {
                        throw new BeansException("ref bean " + pValue + " not found!");
                    }
                }


                // 按照setXxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                Method method = null;
                try {
                    method = clazz.getMethod(methodName, paramTypes);
                    method.invoke(obj, paramValues);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new BeansException("bean setter method invoke error,bean id is " + bd.getId() + ",method is " + methodName);
                }

            }
        }
    }

    private Object initializingBean(String beanName, Object bean, BeanDefinition beanDefinition) throws BeansException {
        // step1. 调用Aware回调，这里我们还不涉及，暂不实现
        // step2. 调用BeanPostProcessor#postProcessBeforeInitialization方法
        Object wrappedBean = bean;
        if (wrappedBean != null) {
            wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
        }

        // step3. 调用自定义方法，这里我们还不涉及，暂不实现
        // step4. 调用BeanPostProcessor#postProcessAfterInitialization方法
        if (wrappedBean != null) {
            wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        }
        return wrappedBean;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            result = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (result == null) {
                return result;
            }
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            result = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (result == null) {
                return result;
            }
        }
        return result;
    }
}
