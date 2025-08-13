package com.products.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {
    
    @GetMapping
    public ResponseEntity<String> healthCheck() {
        String jsonBody = "{\"message\":\"Service ok\"}";
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonBody);
    }
}
