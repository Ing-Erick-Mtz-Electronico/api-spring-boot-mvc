package com.products.api.repository.port;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.products.api.repository.entity.ProductEntity;

public interface IProductPortRepository {

    List<ProductEntity> findPageWithFilters(String name, String category, Pageable pageable);
    
    Optional<ProductEntity> findById(Long id);
}
