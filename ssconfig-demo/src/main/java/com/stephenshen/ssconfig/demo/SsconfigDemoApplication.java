package com.stephenshen.ssconfig.demo;

import com.stephenshen.ssconfig.client.annotation.EnableSSConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties(SSDemoConfig.class)
@EnableSSConfig
public class SsconfigDemoApplication {

    @Value("${ss.a}")
    private String a;

    @Autowired
    private SSDemoConfig ssDemoConfig;

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(SsconfigDemoApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        System.out.println(Arrays.toString(environment.getActiveProfiles()));
        return args -> {
            System.out.println(a);
            System.out.println(ssDemoConfig.getA());
        };
    }

}
