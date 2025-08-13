package com.products.api.builder;

import com.products.api.dto.product.ProductToListDto;
import com.products.api.dto.product.ProductWithDetailDto;
import com.products.api.model.product.ProductModel;
import com.products.api.model.product.CategoryModel;
import com.products.api.model.product.ImageModel;
import com.products.api.model.product.AttributeModel;
import com.products.api.dto.product.CategoryDto;
import com.products.api.dto.product.ImageDto;
import com.products.api.dto.product.AttributeDto;

public interface ProductDtoBuilder {

    default ProductToListDto toBasicProductDto(ProductModel productModel) {
        return ProductToListDto.builder()
            .id(productModel.getId())
            .name(productModel.getName())
            .description(productModel.getDescription())
            .price(productModel.getPrice())
            .build();
    }

    default ProductWithDetailDto toFullProductDto(ProductModel productModel) {
        return ProductWithDetailDto.builder()
            .id(productModel.getId())
            .name(productModel.getName())
            .description(productModel.getDescription())
            .price(productModel.getPrice())
            .categories(productModel.getCategories().stream()
                .map(this::toFullCategoryDto)
                .toList())
            .images(productModel.getImages().stream()
                .map(this::toFullImageDto)
                .toList())
            .attributes(productModel.getAttributes().stream()
                .map(this::toFullAttributeDto)
                .toList())
            .build();
    }

    default CategoryDto toFullCategoryDto(CategoryModel categoryModel) {
        return CategoryDto.builder()
            .id(categoryModel.getId())
            .name(categoryModel.getName())
            .description(categoryModel.getDescription())
            .build();
    }

    default ImageDto toFullImageDto(ImageModel imageModel) {
        return ImageDto.builder()
            .id(imageModel.getId())
            .url(imageModel.getUrl())
            .build();
    }

    default AttributeDto toFullAttributeDto(AttributeModel attributeModel) {
        return AttributeDto.builder()
            .id(attributeModel.getId())
            .name(attributeModel.getName())
            .description(attributeModel.getDescription())
            .build();
    }
}
