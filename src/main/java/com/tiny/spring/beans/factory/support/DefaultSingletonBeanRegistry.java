package com.tiny.spring.beans.factory.support;

import com.tiny.spring.beans.factory.config.SingletonBeanRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: markus
 * @date: 2023/10/7 11:43 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected List<String> beanNames = new ArrayList<>();
    /**
     * 并发安全的单例Bean操作，保证容器中的Bean唯一
     */
    protected Map<String, Object> singletons = new ConcurrentHashMap<>(256);
    /**
     * 早期Bean实例引用（实例被创建但还未被初始化），提前暴露，解决简单的循环依赖问题
     */
    protected final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap(16);
    /**
     * 依赖bean名称之间的映射：bean名称到依赖bean名称集。
     */
    private final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);
    /**
     * 在依赖的bean名称之间映射：bean名称到bean的依赖项的bean名称集。
     */
    private final Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletons) {
            this.singletons.put(beanName, singletonObject);
            this.beanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return this.beanNames.toArray(new String[0]);
    }

    /**
     * 保存当前Bean依赖的bean名称集合
     *
     * @param beanName
     * @param dependentBeanName
     */
    public void registerDependentBean(String beanName, String dependentBeanName) {
        synchronized (this.dependentBeanMap) {
            Set<String> dependentBeans = this.dependentBeanMap.computeIfAbsent(beanName, k -> new LinkedHashSet<>(8));
            if (!dependentBeans.add(dependentBeanName)) {
                // 如果已经添加过了，就直接返回
                return;
            }
        }
        synchronized (this.dependenciesForBeanMap) {
            Set<String> dependenciesForBean =
                    this.dependenciesForBeanMap.computeIfAbsent(dependentBeanName, k -> new LinkedHashSet<>(8));
            dependenciesForBean.add(beanName);
        }
    }

    public String[] getDependentBeans(String beanName) {
        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);

        if (dependentBeans == null || dependentBeans.size() == 0) {
            return new String[0];
        }

        synchronized (this.dependentBeanMap) {
            return dependentBeans.toArray(new String[0]);
        }
    }

    public String[] getDependenciesForBean(String beanName) {
        Set<String> dependenciesForBeans = this.dependenciesForBeanMap.get(beanName);

        if (dependenciesForBeans == null || dependenciesForBeans.size() == 0) {
            return new String[0];
        }

        synchronized (this.dependenciesForBeanMap) {
            return dependenciesForBeans.toArray(new String[0]);
        }
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletons) {
            this.beanNames.remove(beanName);
            this.singletons.remove(beanName);
        }
    }
}
