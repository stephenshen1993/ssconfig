package com.stephenshen.ssconfig.client.repository;

import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import com.stephenshen.ssconfig.client.config.ConfigMeta;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * default impl for ss repository
 * @author stephenshen
 * @date 2024/5/5 12:15:09
 */
@AllArgsConstructor
public class SSRepositoryImpl implements SSRepository {

    ConfigMeta meta;

    @Override
    public Map<String, String> getConfig() {
        String listPath = meta.getConfigServer() + "/list?app=" +meta.getApp() + "&env=" + meta.getEnv() + "&ns=" + meta.getNs() ;
        List<Configs> configsList = HttpUtils.httpGet(listPath, new TypeReference<>() {});
        Map<String, String> resultMap = new HashMap<>();
        configsList.forEach(c -> resultMap.put(c.getPkey(), c.getPval()));
        return resultMap;
    }
}
