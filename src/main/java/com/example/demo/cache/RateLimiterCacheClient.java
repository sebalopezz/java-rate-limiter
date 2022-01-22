package com.example.demo.cache;

import com.example.demo.utils.RateLimiter;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterCacheClient extends LocalCacheClient<String, RateLimiter> {
    private static final Integer RATE_LIMITER_CACHE_TTL = 60;

    public RateLimiterCacheClient() {
        super(RATE_LIMITER_CACHE_TTL);
    }
}
