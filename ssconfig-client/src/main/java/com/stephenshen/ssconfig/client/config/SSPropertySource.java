package com.stephenshen.ssconfig.client.config;

import org.springframework.core.env.EnumerablePropertySource;

/**
 * ss property source
 *
 * @author stephenshen
 * @date 2024/5/3 18:31:58
 */
public class SSPropertySource extends EnumerablePropertySource<SSConfigService> {

    public SSPropertySource(String name, SSConfigService source) {
        super(name, source);
    }

    protected SSPropertySource(String name) {
        super(name);
    }

    @Override
    public String[] getPropertyNames() {
        return source.getPropertyNames();
    }

    @Override
    public Object getProperty(String name) {
        return source.getProperty(name);
    }
}
