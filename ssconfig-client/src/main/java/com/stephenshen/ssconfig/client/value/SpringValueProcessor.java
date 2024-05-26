package com.stephenshen.ssconfig.client.value;

import cn.kimmking.utils.FieldUtils;
import com.stephenshen.ssconfig.client.util.PlaceholderHelper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * process spring value.
 * 1.扫描所有的spring value，保存起来
 * 2.在配置变更时，更新所有的spring value
 * @author stephenshen
 * @date 2024/5/26 11:12:07
 */
@Slf4j
public class SpringValueProcessor implements BeanPostProcessor, BeanFactoryAware, ApplicationListener<EnvironmentChangeEvent> {

    static final PlaceholderHelper helper = PlaceholderHelper.getInstance();
    static final MultiValueMap<String, SpringValue> VALUE_HOLDER = new LinkedMultiValueMap<>();
    @Setter
    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        FieldUtils.findAnnotatedField(bean.getClass(), Value.class).forEach(field -> {
            log.info("[SSCONFIG] >> find spring value: {}", field);
            Value value = field.getAnnotation(Value.class);
            helper.extractPlaceholderKeys(value.value()).forEach(key -> {
                log.info("[SSCONFIG] >> find spring value key: {}", key);
                SpringValue springValue = new SpringValue(bean, beanName, key, value.value(), field);
                VALUE_HOLDER.add(key, springValue);
            });
        });
        return bean;
    }

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        log.info("[SSCONFIG] >> update spring value for keys: {}", event.getKeys());
        event.getKeys().forEach(key -> {
            log.info("[SSCONFIG] >> update spring value: {}", key);
            List<SpringValue> springValues = VALUE_HOLDER.get(key);
            if (springValues == null || springValues.isEmpty()) {
                return;
            }
            springValues.forEach(springValue -> {
                log.info("[SSCONFIG] >> update spring value: {} for key {}", springValue, key);
                try {
                    Object value = helper.resolvePropertyValue((ConfigurableBeanFactory) beanFactory,
                            springValue.getBeanName(), springValue.getPlaceHolder());
                    log.info("[SSCONFIG] >> update value: {} for holder {}", value, springValue.getPlaceHolder());
                    springValue.getField().setAccessible(true);
                    springValue.getField().set(springValue.getBean(), value);
                } catch (Exception ex) {
                    log.error("[SSCONFIG] >> update spring value error", ex);
                }
            });
        });
    }
}
