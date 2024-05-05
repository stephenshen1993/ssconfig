package com.stephenshen.ssconfig.client.config;

import com.stephenshen.ssconfig.client.repository.SSRepository;
import com.stephenshen.ssconfig.client.repository.SSRepositoryChangeListener;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * ss config service
 *
 * @author stephenshen
 * @date 2024/5/3 18:34:31
 */
public interface SSConfigService extends SSRepositoryChangeListener {

    static SSConfigService getDefault(ApplicationContext applicationContext, ConfigMeta meta) {
        SSRepository repository = SSRepository.getDefault(applicationContext, meta);
        Map<String, String> config = repository.getConfig();
        SSConfigServiceImpl configService = new SSConfigServiceImpl(applicationContext, config);
        repository.addListener(configService);
        return configService;
    }

    String[] getPropertyNames();

    String getProperty(String name);
}
