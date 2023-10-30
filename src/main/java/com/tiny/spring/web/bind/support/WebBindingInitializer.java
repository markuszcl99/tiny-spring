package com.tiny.spring.web.bind.support;

import com.tiny.spring.web.bind.WebDataBinder;

/**
 * @author: markus
 * @date: 2023/10/30 11:09 PM
 * @Description: 数据绑定初始化器
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface WebBindingInitializer {
    void initBinder(WebDataBinder binder);
}
