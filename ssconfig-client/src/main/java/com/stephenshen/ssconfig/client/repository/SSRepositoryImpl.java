package com.stephenshen.ssconfig.client.repository;

import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import com.stephenshen.ssconfig.client.config.ConfigMeta;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * default impl for ss repository
 * @author stephenshen
 * @date 2024/5/5 12:15:09
 */
public class SSRepositoryImpl implements SSRepository {
    ConfigMeta meta;
    Map<String, Long> versionMap = new HashMap<>();
    Map<String, Map<String, String>> configMap = new HashMap<>();
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    List<SSRepositoryChangeListener> listeners = new ArrayList<>();

    public SSRepositoryImpl(ApplicationContext applicationContext, ConfigMeta meta) {
        this.meta = meta;
        executor.scheduleWithFixedDelay(this::heartbeat, 1000, 5000, TimeUnit.MILLISECONDS);
    }

    public void addListener(SSRepositoryChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public Map<String, String> getConfig() {
        String key = meta.genKey();
        if (configMap.containsKey(key)) {
            return configMap.get(key);
        }
        return findAll();
    }

    private Map<String, String> findAll() {
        String listPath = meta.listPath();
        System.out.println("[SSCONFIG] list all configs from ss config server");
        List<Configs> configsList = HttpUtils.httpGet(listPath, new TypeReference<>() {});
        Map<String, String> resultMap = new HashMap<>();
        configsList.forEach(c -> resultMap.put(c.getPkey(), c.getPval()));
        return resultMap;
    }

    private void heartbeat() {
        String versionPath = meta.versionPath();
        Long version = HttpUtils.httpGet(versionPath, new TypeReference<>() {});
        String key = meta.genKey();
        Long oldVersion = versionMap.getOrDefault(key, -1L);
        if (version > oldVersion) { // 发生了变化
            System.out.println("[SSCONFIG] current=" + version + ", old=" + oldVersion);
            System.out.println("[SSCONFIG] need update new configs");
            versionMap.put(key, version);
            Map<String, String> newConfigs = findAll();
            configMap.put(key, newConfigs);
            listeners.forEach(l -> l.onChange(new SSRepositoryChangeListener.ChangeEvent(meta, newConfigs)));
        }
    }
}
