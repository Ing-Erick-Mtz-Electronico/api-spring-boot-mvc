package com.products.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.api.dto.healthCheck.HealthCheckDto;
import com.products.api.exception.ErrorDetail;

import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {
    
    @Operation(
            summary = "Health check",
            description = "Este endpoint obtiene el estado de salud de la API",
            responses = {
                
                @ApiResponse(
                    responseCode = "200",
                    description = "Estado de salud de la API",
                    content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = HealthCheckDto.class)
                    )
                ),
                @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorDetail.class)
                    )
                )
            }
    )
    @GetMapping
    public ResponseEntity<HealthCheckDto> healthCheck() {
        HealthCheckDto healthCheckDto = new HealthCheckDto("Service ok");
        return ResponseEntity.ok()
                .body(healthCheckDto);
    }
}
