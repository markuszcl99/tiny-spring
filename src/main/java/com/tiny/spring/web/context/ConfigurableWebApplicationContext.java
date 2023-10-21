package com.tiny.spring.web.context;

import com.tiny.spring.context.ConfigurableApplicationContext;

/**
 * @author: markus
 * @date: 2023/10/21 12:12 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface ConfigurableWebApplicationContext extends WebApplicationContext, ConfigurableApplicationContext {

    void setConfigLocation(String configLocation);
}
