package com.tiny.spring.context.support;

import com.tiny.spring.beans.factory.config.BeanPostProcessor;
import com.tiny.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.tiny.spring.beans.factory.support.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: markus
 * @date: 2023/11/12 3:22 PM
 * @Description: 注册器代理
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class PostProcessorRegistrationDelegate {
    public static void registerBeanPostProcessors(
            ConfigurableListableBeanFactory beanFactory, AbstractApplicationContext applicationContext) {
        // 从容器获取BeanPostProcessor候选集合
        String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class);
        // todo 优先级问题（我们这里先不考虑，直接先注册）
        List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
        for (String postProcessorName : postProcessorNames) {
            BeanPostProcessor bp = (BeanPostProcessor) beanFactory.getBean(postProcessorName);
            beanPostProcessors.add(bp);
        }
        // 直接注册
        registerBeanPostProcessors(beanFactory, beanPostProcessors);
    }

    private static void registerBeanPostProcessors(
            ConfigurableListableBeanFactory beanFactory, List<BeanPostProcessor> postProcessors) {

        if (beanFactory instanceof AbstractBeanFactory) {
            // Bulk addition is more efficient against our CopyOnWriteArrayList there
            ((AbstractBeanFactory) beanFactory).addBeanPostProcessors(postProcessors);
        } else {
            for (BeanPostProcessor postProcessor : postProcessors) {
                beanFactory.addBeanPostProcessor(postProcessor);
            }
        }
    }
}
