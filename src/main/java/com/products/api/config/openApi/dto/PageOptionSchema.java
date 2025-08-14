package com.products.api.config.openApi.dto;

public record PageOptionSchema(
    int size,
    int number,
    long totalElements,
    int totalPages
) {
    
}
