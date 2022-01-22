package com.example.demo.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Duration;

public class LocalCacheClient<K,V> {
    private final Cache<K,V> cache;

    public LocalCacheClient(int ttlSeconds) {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(ttlSeconds))
                .recordStats()
                .build();
    }

    public V get(K key) {
        return cache.getIfPresent(key);
    }

    public void put(K key, V value) {
        cache.put(key, value);
    }
}
