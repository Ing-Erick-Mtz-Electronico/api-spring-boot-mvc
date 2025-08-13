package com.products.api.repository.adapter;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.products.api.builder.ProductModelBuilder;
import com.products.api.model.product.ProductModel;

import lombok.RequiredArgsConstructor;

import com.products.api.repository.adapter.out.IPruductJpaRepository;
import com.products.api.repository.port.IProductPortRepository;

@Repository
@RequiredArgsConstructor
public class IProductAdapterRepository implements IProductPortRepository, ProductModelBuilder {

    private final IPruductJpaRepository productJpaRepository;

    @Override
    public Page<ProductModel> findPageWithFilters(String name, String category, Pageable pageable) {
        return productJpaRepository.findPageWithFilters(name, category, pageable)
            .map(this::toBasicProductModel);
    }

    @Override
    public Optional<ProductModel> findById(Long id) {
        return productJpaRepository.findById(id)
            .map(this::toFullProductModel);
    }
    
}
