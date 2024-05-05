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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties(SSDemoConfig.class)
@EnableSSConfig
@RestController
public class SsconfigDemoApplication {

    @Value("${ss.a}")
    private String a;

    @Value("${ss.b}")
    private String b;

    @Autowired
    private SSDemoConfig ssDemoConfig;

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(SsconfigDemoApplication.class, args);
    }

    @GetMapping("/demo")
    public String demo() {
        return "ss.a = " + a + "\n"
                + "ss.b = " + b + "\n"
                + "demo.a = " + ssDemoConfig.getA() + "\n"
                + "demo.b = " + ssDemoConfig.getB() + "\n";
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
