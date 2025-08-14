package com.products.api.dto.product;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductWithDetailDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private List<CategoryDto> categories;
    private List<ImageDto> images;
    private List<AttributeDto> attributes;
}
