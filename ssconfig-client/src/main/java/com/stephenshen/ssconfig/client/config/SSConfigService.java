package com.stephenshen.ssconfig.client.config;

import com.stephenshen.ssconfig.client.repository.SSRepository;

/**
 * ss config service
 *
 * @author stephenshen
 * @date 2024/5/3 18:34:31
 */
public interface SSConfigService {

    static SSConfigService getDefault(ConfigMeta meta) {
        SSRepository repository = SSRepository.getDefault(meta);
        return new SSConfigServiceImpl(repository.getConfig());
    }

    String[] getPropertyNames();

    String getProperty(String name);
}
