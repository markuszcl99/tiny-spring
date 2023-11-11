package com.tiny.spring.beans.factory;

/**
 * @author: markus
 * @date: 2023/11/11 10:37 PM
 * @Description: 具备可继承的BeanFactory
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface HierarchicalBeanFactory extends BeanFactory {

    /**
     * 获得父容器
     *
     * @return
     */
    BeanFactory getParentBeanFactory();

    /**
     * 当前容器（不包含父容器），是否包含指定名称的Bean
     * @param beanName
     * @return
     */
    boolean containsLocalBean(String beanName);
}
