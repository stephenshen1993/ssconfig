package com.stephenshen.ssconfig.client.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stephenshen
 * @date 2024/5/3 18:43:23
 */
@Data
public class PropertySourceProcessor implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered {

    private static final String SS_PROPERTY_SOURCES = "SSPropertySources";
    private static final String SS_PROPERTY_SOURCE = "SSPropertySource";
    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment env = (ConfigurableEnvironment) environment;
        if (env.getPropertySources().contains(SS_PROPERTY_SOURCES)) {
            return;
        }
        // 通过 http 请求，去 ssconfig-server 获取配置 TODO
        Map<String, String> config = new HashMap<>();
        config.put("ss.a", "dev500");
        config.put("ss.b", "b600");
        config.put("ss.c", "c700");
        SSConfigService configService = new SSConfigServiceImpl(config);
        SSPropertySource propertySource = new SSPropertySource(SS_PROPERTY_SOURCE, configService);
        CompositePropertySource compositePropertySource = new CompositePropertySource(SS_PROPERTY_SOURCES);
        compositePropertySource.addPropertySource(propertySource);
        env.getPropertySources().addFirst(compositePropertySource);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
