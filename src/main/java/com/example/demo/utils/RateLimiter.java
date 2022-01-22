package com.example.demo.utils;

import java.util.ArrayList;

public class RateLimiter {

    private static final long MAX_REQUESTS = 5L;
    private static final long PERIOD_IN_NANO = 10000000000L; // 10 seconds

    private final ArrayList<Long> lastRequests;

    public RateLimiter() {
        lastRequests = new ArrayList<>();
    }

    public synchronized boolean allowRequest() {
        long now = System.nanoTime();

        lastRequests.removeIf(req -> (req < now - PERIOD_IN_NANO));

        if (lastRequests.size() < MAX_REQUESTS) {
            lastRequests.add(now);
            return true;
        } else {
            return false;
        }
    }

}
