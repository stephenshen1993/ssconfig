package com.stephenshen.ssconfig.client.config;

/**
 * ss config service
 *
 * @author stephenshen
 * @date 2024/5/3 18:34:31
 */
public interface SSConfigService {

    String[] getPropertyNames();

    String getProperty(String name);
}
