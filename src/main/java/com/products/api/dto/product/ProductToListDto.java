package com.products.api.dto.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductToListDto {
    private Long id;
    private String name;
    private String description;
}
