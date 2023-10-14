package com.tiny.spring.beans.factory.annotation;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.config.BeanPostProcessor;
import com.tiny.spring.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Field;

/**
 * @author: markus
 * @date: 2023/10/13 10:51 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length > 0) {
            // 遍历判断每一个属性，如果带有Autowired注解则进行处理
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired) {
                    // 根据属性名查找同名的bean
                    String fieldName = field.getName();
                    Object autowiredObj = this.getBeanFactory().getBean(fieldName);
                    // 设置属性值，完成注入
                    try {
                        // 值true表示反射对象在使用时应禁止Java语言访问检查
                        field.setAccessible(true);
                        field.set(bean, autowiredObj);
                        System.out.println("autowire " + fieldName + " for bean " + beanName);
                    } catch (IllegalAccessException e) {
                        throw new BeansException("autowire " + fieldName + " for bean " + beanName + " error!");
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
