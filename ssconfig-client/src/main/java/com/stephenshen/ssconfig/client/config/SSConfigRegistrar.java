package com.stephenshen.ssconfig.client.config;

import com.stephenshen.ssconfig.client.value.SpringValueProcessor;
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
        registerClass(registry, PropertySourceProcessor.class);
        registerClass(registry, SpringValueProcessor.class);
    }

    private static void registerClass(BeanDefinitionRegistry registry, Class<?> aClass) {
        String beanName = aClass.getName();
        System.out.println("register " + beanName);
        boolean hasRegistered = Arrays.asList(registry.getBeanDefinitionNames()).contains(beanName);
        if (hasRegistered) {
            System.out.println(beanName + " already registered");
            return;
        }
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(aClass).getBeanDefinition();
        registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
