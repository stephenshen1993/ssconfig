package com.stephenshen.ssconfig.client.value;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * spring value.
 * @author stephenshen
 * @date 2024/5/26 11:32:58
 */
@Data
@AllArgsConstructor
public class SpringValue {
    private Object bean;
    private String beanName;
    private String key;
    private String placeHolder;
    private Field field;
}
