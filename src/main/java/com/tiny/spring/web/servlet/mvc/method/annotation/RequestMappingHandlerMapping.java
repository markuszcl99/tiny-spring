package com.tiny.spring.web.servlet.mvc.method.annotation;

import com.sun.istack.internal.Nullable;
import com.tiny.spring.beans.factory.InitializingBean;
import com.tiny.spring.core.annotation.AnnotatedElementUtils;
import com.tiny.spring.stereotype.Controller;
import com.tiny.spring.web.bind.annotation.RequestMapping;
import com.tiny.spring.web.bind.support.WebBindingInitializer;
import com.tiny.spring.web.context.support.WebApplicationObjectSupport;
import com.tiny.spring.web.method.HandlerMethod;
import com.tiny.spring.web.method.MappingRegistry;
import com.tiny.spring.web.servlet.HandlerExecutionChain;
import com.tiny.spring.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: markus
 * @date: 2023/10/26 11:03 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class RequestMappingHandlerMapping extends WebApplicationObjectSupport implements HandlerMapping, InitializingBean {

    private final MappingRegistry mappingRegistry = new MappingRegistry();


    public RequestMappingHandlerMapping() {
    }

    @Override
    @Nullable
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        Method method = mappingRegistry.getMappingMethods().get(uri);
        Object bean = mappingRegistry.getMappingObjs().get(uri);
        if (method == null || bean == null) {
            return null;
        }
        HandlerMethod handlerMethod = new HandlerMethod(bean, method);
        return new HandlerExecutionChain(handlerMethod);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 处理其他信息，我们不管
        // 这里获取容器中的Controller信息
        initHandlerMethods();
    }

    private void initHandlerMethods() {
        for (String candidateBeanName : getCandidateBeanNames()) {
            // todo 后续需要考虑代理，我们这里先暂时不考虑，待引入AOP后，再考虑这块
            processCandidateBean(candidateBeanName);
        }
    }

    private void processCandidateBean(String candidateBeanName) {
        Class<?> beanType = getApplicationContext().getType(candidateBeanName);
        if (beanType != null && isHandler(beanType)) {
            Object handler = getApplicationContext().getBean(candidateBeanName);
            detectHandlerMethods(handler);
        }
    }

    private void detectHandlerMethods(Object handler) {
        Class<?> handlerType = (handler instanceof String ? getApplicationContext().getType((String) handler) : handler.getClass());
        if (handlerType != null) {
            // todo 这里其实也是需要考虑AOP代理的，不过我们现在先不管，后面再说
            // 遍历这个类里面的方法，然后构建一个url到controller的映射
            // 获取当前类是否被@RequestMapping标注，如果被标注了，就获取它的url，如果没有则忽略
            String basePath = tryGetPath(handlerType);
            for (Method method : getHandlerMethod(handlerType)) {
                StringBuilder mappingSb = new StringBuilder();
                mappingSb.append(basePath)
                        .append(tryGetPath(method));
                String urlMapping = mappingSb.toString();
                if (urlMapping.isEmpty()) {
                    throw new IllegalArgumentException("The path does not conform to specifications");
                }
                this.mappingRegistry.register(urlMapping, handler, method);
            }
        }
    }

    private List<Method> getHandlerMethod(Class<?> handlerType) {
        Method[] candidateMethods = handlerType.getMethods();
        List<Method> result = new ArrayList<>();
        for (Method candidateMethod : candidateMethods) {
            boolean hasRequestMappingAnnotation = AnnotatedElementUtils.hasAnnotation(candidateMethod, RequestMapping.class);
            if (hasRequestMappingAnnotation) {
                result.add(candidateMethod);
            }
        }
        return result;
    }

    private String tryGetPath(Class<?> handlerType) {
        RequestMapping annotation = handlerType.getAnnotation(RequestMapping.class);
        if (annotation != null) {
            return annotation.value();
        }
        return "";
    }

    @Nullable
    private String tryGetPath(Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        if (annotation != null) {
            return annotation.value();
        }
        return null;
    }

    private boolean isHandler(Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) ||
                AnnotatedElementUtils.hasAnnotation(beanType, RequestMapping.class);
    }

    private String[] getCandidateBeanNames() {
        return getApplicationContext().getBeanNamesForType(Object.class);
    }
}
