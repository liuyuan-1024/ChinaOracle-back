package com.liuyuan.chinaoracle.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Spring 上下文获取工具.
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    /**
     * 应用上下文对象.
     */
    private static ApplicationContext applicationContext;

    /**
     * 通过名称获取 Bean.
     *
     * @param beanName bean的名称
     * @return bean
     */
    public static Object getBean(final String beanName) {
        return applicationContext.getBean(beanName);
    }

    /**
     * 通过 class 获取 Bean.
     *
     * @param beanClass bean的Class
     * @param <T>       泛型
     * @return bean
     */
    public static <T> T getBean(final Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    /**
     * 通过名称和类型获取 Bean.
     *
     * @param beanName  bean的名称
     * @param beanClass bean的Class
     * @param <T>       泛型
     * @return bean
     */
    public static <T> T getBean(final String beanName,
                                final Class<T> beanClass) {
        return applicationContext.getBean(beanName, beanClass);
    }

    /**
     * 设置应用上下文.
     *
     * @param applicationContext the ApplicationContext object to be used by
     *                           this object
     * @throws BeansException bean异常
     */
    @Override
    public void setApplicationContext(
        @NotNull final ApplicationContext applicationContext)
        throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }
}