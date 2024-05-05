package com.stephenshen.ssconfig.client.annotation;

import com.stephenshen.ssconfig.client.config.SSConfigRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * SSConfig client entrypoint.
 *
 * @author stephenshen
 * @date 2024/5/3 17:43:10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Import({SSConfigRegistrar.class})
public @interface EnableSSConfig {
}
