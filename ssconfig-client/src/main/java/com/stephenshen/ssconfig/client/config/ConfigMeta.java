package com.stephenshen.ssconfig.client.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author stephenshen
 * @date 2024/5/5 12:27:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigMeta {
    String app;
    String env;
    String ns;
    String configServer;
}
