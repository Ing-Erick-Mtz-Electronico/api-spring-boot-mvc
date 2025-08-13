package com.products.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.products.api.builder.ProductBuilder;
import com.products.api.model.product.ProductModel;
import com.products.api.repository.port.IProductPortRepository;

public class ProductService implements ProductBuilder {
    
    private final IProductPortRepository productPortRepository;

    public ProductService(IProductPortRepository productPortRepository) {
        this.productPortRepository = productPortRepository;
    }

    public List<ProductModel> findPageWithFilters(String name, String category, Pageable pageable) {
        return productPortRepository.findPageWithFilters(name, category, pageable).stream()
            .map(this::toProductModel)
            .toList();
    }

    public Optional<ProductModel> findById(Long id) {
        return productPortRepository.findById(id)
            .map(this::toProductModel);
    }
}
