package com.stephenshen.ssconfig.client.config;

import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @author stephenshen
 * @date 2024/5/3 18:40:29
 */
public class SSConfigServiceImpl implements SSConfigService {

    Map<String, String> config;
    ApplicationContext applicationContext;

    public SSConfigServiceImpl(ApplicationContext applicationContext, Map<String, String> config) {
        this.applicationContext = applicationContext;
        this.config = config;
    }

    public String[] getPropertyNames() {
        return config.keySet().toArray(new String[0]);
    }

    public String getProperty(String name) {
        return config.get(name);
    }

    @Override
    public void onChange(ChangeEvent event) {
        this.config = event.config();
        if (!config.isEmpty()) {
            System.out.println("[SSCONFIG] fire an environmentChangeEvent with keys:" + config.keySet());
            applicationContext.publishEvent(new EnvironmentChangeEvent(config.keySet()));
        }
    }
}
