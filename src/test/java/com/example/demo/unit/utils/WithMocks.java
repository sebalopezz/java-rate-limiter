package com.example.demo.unit.utils;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public interface WithMocks {
    @BeforeEach
    default void setupMocks() {
        MockitoAnnotations.openMocks(this);
    }
}
