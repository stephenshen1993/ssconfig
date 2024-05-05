package com.stephenshen.ssconfig.client.repository;

import com.stephenshen.ssconfig.client.config.ConfigMeta;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * interface to get config from remote
 *
 * @author stephenshen
 * @date 2024/5/5 12:13:49
 */
public interface SSRepository {

    static SSRepository getDefault(ApplicationContext applicationContext, ConfigMeta meta) {
        return new SSRepositoryImpl(applicationContext, meta);
    }

    Map<String, String> getConfig();

    void addListener(SSRepositoryChangeListener listener);
}
