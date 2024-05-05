package com.stephenshen.ssconfig.client.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * @author stephenshen
 * @date 2024/5/3 18:43:23
 */
@Data
public class PropertySourceProcessor implements BeanFactoryPostProcessor, ApplicationContextAware, EnvironmentAware, PriorityOrdered {

    private static final String SS_PROPERTY_SOURCES = "SSPropertySources";
    private static final String SS_PROPERTY_SOURCE = "SSPropertySource";
    private Environment environment;
    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
        if (configurableEnvironment.getPropertySources().contains(SS_PROPERTY_SOURCES)) {
            return;
        }
        // 通过 http 请求，去 ssconfig-server 获取配置 TODO

        String app = configurableEnvironment.getProperty("ssconfig.app", "app1");
        String env = configurableEnvironment.getProperty("ssconfig.env", "dev");
        String ns = configurableEnvironment.getProperty("ssconfig.ns", "public");
        String configServer = configurableEnvironment.getProperty("ssconfig.configServer", "http://localhost:9129");

        ConfigMeta configMeta = new ConfigMeta(app, env, ns, configServer);


        SSConfigService configService = SSConfigService.getDefault(applicationContext, configMeta);
        SSPropertySource propertySource = new SSPropertySource(SS_PROPERTY_SOURCE, configService);
        CompositePropertySource compositePropertySource = new CompositePropertySource(SS_PROPERTY_SOURCES);
        compositePropertySource.addPropertySource(propertySource);
        configurableEnvironment.getPropertySources().addFirst(compositePropertySource);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
