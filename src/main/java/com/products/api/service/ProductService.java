package com.products.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.products.api.builder.ProductDtoBuilder;
import com.products.api.dto.product.ProductToListDto;
import com.products.api.dto.product.ProductWithDetailDto;
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

    public Optional<ProductWithDetailDto> findById(Long id) {
        return productPortRepository.findById(id)
            .map(this::toFullProductDto);
    }
}
