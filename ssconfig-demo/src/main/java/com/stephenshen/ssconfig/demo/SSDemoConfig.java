package com.stephenshen.ssconfig.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * test demo config
 *
 * @author stephenshen
 * @date 2024/5/3 17:55:22
 */
@Data
@ConfigurationProperties(prefix = "ss")
public class SSDemoConfig {
    String a;
    String b;
}
