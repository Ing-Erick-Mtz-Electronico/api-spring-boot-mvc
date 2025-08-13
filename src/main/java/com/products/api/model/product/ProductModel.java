package com.products.api.model.product;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class ProductModel extends BaseModel {
    
    private String name;
    private String description;
    private BigDecimal price;
    private List<CategoryModel> categories;
    private List<ImageModel> images;
    private List<AttributeModel> attributes;
}
