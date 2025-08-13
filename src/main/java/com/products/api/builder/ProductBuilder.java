package com.products.api.builder;

import com.products.api.model.product.ProductModel;
import com.products.api.model.product.CategoryModel;
import com.products.api.model.product.ImageModel;
import com.products.api.model.product.AttributeModel;
import com.products.api.repository.entity.AttributeEntity;
import com.products.api.repository.entity.CategoryEntity;
import com.products.api.repository.entity.ImageEntity;
import com.products.api.repository.entity.ProductEntity;

public interface ProductBuilder {
    default ProductModel toProductModel(ProductEntity productEntity) {
        return ProductModel.builder()
            .id(productEntity.getId())
            .name(productEntity.getName())
            .description(productEntity.getDescription())
            .price(productEntity.getPrice())
            .categories(productEntity.getCategories().stream()
                .map(this::toCategoryModel)
                .toList())
            .images(productEntity.getImages().stream()
                .map(this::toImageModel)
                .toList())
            .attributes(productEntity.getAttributes().stream()
                .map(this::toAttributeModel)
                .toList())
            .build();
    }

    default CategoryModel toCategoryModel(CategoryEntity categoryEntity) {
        return CategoryModel.builder()
            .id(categoryEntity.getId())
            .name(categoryEntity.getName())
            .description(categoryEntity.getDescription())
            .build();
    }

    default ImageModel toImageModel(ImageEntity imageEntity) {
        return ImageModel.builder()
            .id(imageEntity.getId())
            .url(imageEntity.getUrl())
            .build();
    }

    default AttributeModel toAttributeModel(AttributeEntity attributeEntity) {
        return AttributeModel.builder()
            .id(attributeEntity.getId())
            .name(attributeEntity.getName())
            .description(attributeEntity.getDescription())
            .build();
    }
}
