package com.stephenshen.ssconfig.client.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * config meta info.
 *
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

    public String genKey() {
        return String.format("%s_%s_%s", app, env, ns);
    }

    public String listPath() {
        return path("list");
    }

    public String versionPath() {
        return path("version");
    }

    private String path(String context) {
        return String.format("%s/%s?app=%s&env=%s&ns=%s", configServer, context, app, env, ns);
    }
}
