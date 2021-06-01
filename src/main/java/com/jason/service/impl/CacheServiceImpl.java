package com.jason.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jason.service.CacheService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {

    private Cache<String,Object> commonCache = null;

    @PostConstruct
    public void init(){
        commonCache= CacheBuilder.newBuilder()
                //缓存容器的初始容量
                .initialCapacity(10)
                //缓存中最大可以存储100个KEY，超过后会按照LRU策略移除
                .maximumSize(100)
                //设置写缓存后多少秒过期，还有根据访问过期即expireAfterAccess
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public void setCommonCache(String key, Object value) {
        commonCache.put(key,value);
    }

    @Override
    public Object getFromCommonCache(String key) {
        return commonCache.getIfPresent(key);
    }
}