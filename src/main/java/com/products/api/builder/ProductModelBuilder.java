package com.products.api.builder;

import java.util.List;
import java.util.Optional;

import com.products.api.model.product.ProductModel;
import com.products.api.model.product.CategoryModel;
import com.products.api.model.product.ImageModel;
import com.products.api.model.product.AttributeModel;
import com.products.api.repository.entity.AttributeEntity;
import com.products.api.repository.entity.CategoryEntity;
import com.products.api.repository.entity.ImageEntity;
import com.products.api.repository.entity.ProductEntity;

public interface ProductModelBuilder {

    default ProductModel toFullProductModel(ProductEntity productEntity) {
        return ProductModel.builder()
            .id(productEntity.getId())
            .name(productEntity.getName())
            .description(productEntity.getDescription())
            .price(productEntity.getPrice())
            .categories(Optional.ofNullable(productEntity.getCategories())
                .orElse(List.of())
                    .stream()
                    .map(this::toFullCategoryModel)
                    .toList())
            .images(Optional.ofNullable(productEntity.getImages())
                .orElse(List.of())
                    .stream()
                    .map(this::toFullImageModel)
                    .toList())
            .attributes(Optional.ofNullable(productEntity.getAttributes())
                .orElse(List.of())
                    .stream()
                    .map(this::toFullAttributeModel)
                    .toList())
            .build();
    }

    default CategoryModel toFullCategoryModel(CategoryEntity categoryEntity) {
        return CategoryModel.builder()
            .id(categoryEntity.getId())
            .name(categoryEntity.getName())
            .description(categoryEntity.getDescription())
            .build();
    }

    default ImageModel toFullImageModel(ImageEntity imageEntity) {
        return ImageModel.builder()
            .id(imageEntity.getId())
            .url(imageEntity.getUrl())
            .build();
    }

    default AttributeModel toFullAttributeModel(AttributeEntity attributeEntity) {
        return AttributeModel.builder()
            .id(attributeEntity.getId())
            .name(attributeEntity.getName())
            .description(attributeEntity.getDescription())
            .build();
    }

    default ProductModel toBasicProductModel(ProductEntity productEntity) {
        return ProductModel.builder()
            .id(productEntity.getId())
            .name(productEntity.getName())
            .description(productEntity.getDescription())
            .price(productEntity.getPrice())
            .build();
    }
}
