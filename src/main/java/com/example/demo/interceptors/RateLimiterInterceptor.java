package com.example.demo.interceptors;

import com.example.demo.cache.RateLimiterCacheClient;
import com.example.demo.utils.RateLimiter;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private static final String HEADER_USER_ID = "user-id";

    @Autowired
    RateLimiterCacheClient rateLimiterCacheClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader(HEADER_USER_ID);

        if (Strings.isNullOrEmpty(userId)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return false;
        }

        RateLimiter rateLimiter = rateLimiterCacheClient.get(userId);

        if (rateLimiter == null) {
            rateLimiter = new RateLimiter();
        }

        if (rateLimiter.allowRequest()) {
            rateLimiterCacheClient.put(userId, rateLimiter);
            response.setStatus(HttpStatus.OK.value());
            return true;
        }

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        return false;
    }
}
