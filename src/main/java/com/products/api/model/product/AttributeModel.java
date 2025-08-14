package com.products.api.model.product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class AttributeModel extends BaseModel {
    private String name;
    private String description;
}
