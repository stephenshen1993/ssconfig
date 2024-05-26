package com.stephenshen.ssconfig.client.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author stephenshen
 * @date 2024/5/3 18:40:29
 */
@Slf4j
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
        Set<String> keys = calcChangeKeys(this.config, event.getConfig());
        if (keys.isEmpty()) {
            log.info("[SSCONFIG] calcChangeKeys return empty, ignore update.");
            return;
        }
        this.config = event.getConfig();
        if (!config.isEmpty()) {
            System.out.println("[SSCONFIG] fire an environmentChangeEvent with keys:" + keys);
            applicationContext.publishEvent(new EnvironmentChangeEvent(keys));
        }
    }

    private Set<String> calcChangeKeys(Map<String, String> oldConfigs, Map<String, String> newConfigs) {
        if (oldConfigs.isEmpty()) return newConfigs.keySet();
        if (newConfigs.isEmpty()) return oldConfigs.keySet();
        Set<String> news = newConfigs.keySet().stream()
                .filter(key -> !oldConfigs.get(key).equals(newConfigs.get(key)))
                .collect(Collectors.toSet());
        oldConfigs.keySet().stream().filter(key -> !newConfigs.containsKey(key)).forEach(news::add);
        return news;
    }
}
