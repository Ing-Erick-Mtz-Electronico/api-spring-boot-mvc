package com.products.api.repository.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import com.products.api.repository.adapter.out.IPruductJpaRepository;
import com.products.api.repository.entity.ProductEntity;
import com.products.api.repository.port.IProductPortRepository;

@Repository
@RequiredArgsConstructor
public class IProductAdapterRepository implements IProductPortRepository {

    private final IPruductJpaRepository productJpaRepository;

    @Override
    public List<ProductEntity> findPageWithFilters(String name, String category, Pageable pageable) {
        return productJpaRepository.findPageWithFilters(name, category, pageable);
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productJpaRepository.findById(id);
    }
    
}
