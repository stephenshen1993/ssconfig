package com.stephenshen.ssconfig.client.config;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;

/**
 * @author stephenshen
 * @date 2024/5/3 18:58:20
 */
public class SSConfigRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry);
        System.out.println("register PropertySourceProcessor");
        String beanName = PropertySourceProcessor.class.getName();
        boolean hasRegistered = Arrays.asList(registry.getBeanDefinitionNames()).contains(beanName);
        if (hasRegistered) {
            System.out.println("PropertySourceProcessor already registered");
            return;
        }
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(PropertySourceProcessor.class).getBeanDefinition();
        registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
