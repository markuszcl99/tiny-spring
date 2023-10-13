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

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿bean实例
        Object singleton = getSingleton(beanName);
        if (singleton == null) {
            // 如果没有实例，则尝试从早期Bean引用缓存中去获取一下 todo 先在这里获取下，后续会像spring源码一样 抽象到DefaultSingletonBeanRegistry中去。
            singleton = this.earlySingletonObjects.get(beanName);
            if (singleton == null) {
                // 如果早期Bean引用缓存中还是没有，那就老老实实创建实例吧
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (beanDefinition == null) {
                    throw new BeansException("The bean name does not exit. bean name [" + beanName + "]");
                } else {
                    // 获取Bean的定义
                    try {
                        singleton = createBean(beanDefinition);
                    } catch (BeansException e) {
                        throw e;
                    }
                    // 注册最终成形的Bean实例
                    this.registerSingleton(beanDefinition.getId(), singleton);
                }
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
        // 创建实例
        Object obj = doCreateBean(beanDefinition);
        // 将创建好的实例 存储到早期Bean引用缓存中
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);
        try {
            clazz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 处理属性
        handleProperties(beanDefinition, clazz, obj);
        return obj;
    }

    /**
     * 仅仅是创建实例对象，并没有赋值
     *
     * @param bd
     * @return
     * @throws BeansException
     */
    private Object doCreateBean(BeanDefinition bd) throws BeansException {
        Class<?> clazz = null;
        Object obj = null;
        Constructor<?> constructor = null;

        try {
            clazz = Class.forName(bd.getClassName());
            // 处理构造器参数
            ArgumentValues argumentValues = bd.getConstructorArgumentValues();
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
            throw new BeansException("bean constructor invoke error,bean id is" + bd.getId());
        }
        return obj;
    }

    private void handleProperties(BeanDefinition bd, Class<?> clazz, Object obj) throws BeansException {
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
}
