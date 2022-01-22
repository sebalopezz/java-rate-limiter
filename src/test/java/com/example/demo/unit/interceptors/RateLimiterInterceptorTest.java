package com.example.demo.unit.interceptors;

import com.example.demo.cache.RateLimiterCacheClient;
import com.example.demo.interceptors.RateLimiterInterceptor;
import com.example.demo.unit.utils.WithMocks;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RateLimiterInterceptorTest implements WithMocks {

    @InjectMocks
    RateLimiterInterceptor rateLimiterInterceptor;

    @Spy
    RateLimiterCacheClient rateLimiterCacheClient;

    @Test
    public void rateLimiterInterceptor_preHandle_ShouldReturnTrue_WhenLessThanFiveRequestsInTenSeconds() throws InterruptedException {
        // Se consume la API 5 veces dentro de un periodo de 10 segundos y esta devuelve los 5 mensajes del servicio
        var userId = "123";

        when(rateLimiterCacheClient.get(userId)).thenCallRealMethod();

        var req = new MockHttpServletRequest();
        req.addHeader("user-id", userId);
        var res = new MockHttpServletResponse();

        for (int i = 1; i <= 5; i++) {
            var response = rateLimiterInterceptor.preHandle(req, res, new Object());
            assertTrue(response);
            assertEquals(HttpStatus.OK.value(), res.getStatus());
        }
    }

    @Test
    public void rateLimiterInterceptor_preHandle_ShouldReturnTrue_WhenMoreThanFiveRequestsInTenSeconds() throws InterruptedException {
        // Se consume la API 6 veces dentro de un periodo de 10 segundos y el sexto llamado devuelve un error.

        var userId = "123";

        when(rateLimiterCacheClient.get(userId)).thenCallRealMethod();

        var req = new MockHttpServletRequest();
        req.addHeader("user-id", userId);
        var res = new MockHttpServletResponse();

        for (int i = 1; i <= 6; i++) {
            var response = rateLimiterInterceptor.preHandle(req, res, new Object());
            if (i <=5) {
                assertTrue(response);
                assertEquals(HttpStatus.OK.value(), res.getStatus());
            } else {
                assertFalse(response);
                assertEquals(HttpStatus.TOO_MANY_REQUESTS.value(), res.getStatus());
            }
        }
    }

    @Test
    public void rateLimiterInterceptor_preHandle_ShouldReturnTrue_WhenSeventhRequestArrivesAfterTenSeconds() throws InterruptedException {
        // Se consume la API 6 veces dentro de un periodo de 10 segundos, se hace un septimo llamado 10 segundos despues del primer llamado y este devuelve un mensaje del servicio

        var userId = "123";

        when(rateLimiterCacheClient.get(userId)).thenCallRealMethod();

        var req = new MockHttpServletRequest();
        req.addHeader("user-id", userId);
        var res = new MockHttpServletResponse();

        for (int i = 1; i <= 6; i++) {
            var response = rateLimiterInterceptor.preHandle(req, res, new Object());
            if (i <=5) {
                assertTrue(response);
            } else {
                assertFalse(response);
                assertEquals(HttpStatus.TOO_MANY_REQUESTS.value(), res.getStatus());
            }
        }

        Thread.sleep(10000);
        var response = rateLimiterInterceptor.preHandle(req, res, new Object());
        assertTrue(response);
        assertEquals(HttpStatus.OK.value(), res.getStatus());
    }
}
