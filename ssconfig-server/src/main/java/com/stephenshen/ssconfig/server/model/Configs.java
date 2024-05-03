package com.stephenshen.ssconfig.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author stephenshen
 * @date 2024/5/3 14:58:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configs {

    private String app;

    private String env;

    private String ns;

    private String pkey;

    private String pval;
}
