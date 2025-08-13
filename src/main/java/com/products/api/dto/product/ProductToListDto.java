package com.products.api.dto.product;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductToListDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
