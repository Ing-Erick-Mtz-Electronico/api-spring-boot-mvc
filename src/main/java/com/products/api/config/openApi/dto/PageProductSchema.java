package com.products.api.config.openApi.dto;

import com.products.api.dto.product.ProductToListDto;

import java.util.List;

public record PageProductSchema(
    List<ProductToListDto> content,
    PageOptionSchema pageable
) {
    
}
