package com.products.api.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

class HealthCheckControllerTest {

    private HealthCheckController healthCheckController;

    @BeforeEach
    void setUp() {
        healthCheckController = new HealthCheckController();
    }

    @Test
    @DisplayName("Should return OK status with correct JSON response")
    void healthCheck_ShouldReturnOkStatus_WithCorrectJsonResponse() {
        // Act
        ResponseEntity<String> response = healthCheckController.healthCheck();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertNotNull(response.getBody());
        assertEquals("{\"message\":\"Service ok\"}", response.getBody());
    }

}
