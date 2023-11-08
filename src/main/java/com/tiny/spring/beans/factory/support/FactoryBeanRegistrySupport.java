package com.tiny.spring.beans.factory.support;


import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.FactoryBean;

/**
 * @author: markus
 * @date: 2023/11/8 8:07 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    protected Class<?> getTypeForFactoryBean(final FactoryBean<?> factoryBean) {
        return factoryBean.getObjectType();
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        Object object = doGetObjectFromFactoryBean(factoryBean, beanName);
        try {
            object = postProcessObjectFromFactoryBean(factoryBean, beanName);
        } catch (BeansException ex) {
            ex.printStackTrace();
        }
        return object;
    }

    /**
     * 从 Factory Bean中获取目标对象
     *
     * @param factoryBean
     * @param beanName
     * @return
     */
    private Object doGetObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        Object object = null;
        try {
            object = factoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }


    protected Object postProcessObjectFromFactoryBean(Object object, String beanName) throws BeansException {
        return object;
    }
}
