package com.products.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import com.products.api.builder.ProductDtoBuilder;
import com.products.api.constant.error.ErrorConstant;
import com.products.api.dto.product.ProductToListDto;
import com.products.api.dto.product.ProductWithDetailDto;
import com.products.api.exception.GenericException;
import com.products.api.service.interfaces.IProductService;
import com.products.api.repository.port.IProductPortRepository;

public class ProductService implements IProductService, ProductDtoBuilder {
    
    private final IProductPortRepository productPortRepository;

    public ProductService(IProductPortRepository productPortRepository) {
        this.productPortRepository = productPortRepository;
    }

    public Page<ProductToListDto> findPageWithFilters(String name, String category, Pageable pageable) {
        return productPortRepository.findPageWithFilters(name, category, pageable)
            .map(this::toBasicProductDto);
    }

    public ProductWithDetailDto findById(Long id) {
        return productPortRepository.findById(id)
            .map(this::toFullProductDto)
            .orElseThrow(() -> new GenericException(ErrorConstant.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
