package com.stephenshen.ssconfig.client.repository;

import com.stephenshen.ssconfig.client.config.ConfigMeta;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * repository change listener
 *
 * @author stephenshen
 * @date 2024/5/5 16:07:27
 */
public interface SSRepositoryChangeListener {

    void onChange(ChangeEvent event);

    @Data
    @AllArgsConstructor
    class ChangeEvent {
        private ConfigMeta meta;
        private Map<String, String> config;
    }
}
