package com.stephenshen.ssconfig.client.config;

import java.util.Map;

/**
 * @author stephenshen
 * @date 2024/5/3 18:40:29
 */
public class SSConfigServiceImpl implements SSConfigService{

    Map<String, String> config;

    public SSConfigServiceImpl(Map<String, String> config) {
        this.config = config;
    }

    public String[] getPropertyNames() {
        return config.keySet().toArray(new String[0]);
    }

    public String getProperty(String name) {
        return config.get(name);
    }
}
