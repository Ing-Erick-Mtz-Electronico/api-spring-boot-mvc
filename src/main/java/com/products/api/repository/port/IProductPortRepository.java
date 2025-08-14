package com.products.api.repository.port;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.products.api.model.product.ProductModel;

public interface IProductPortRepository {

    Page<ProductModel> findPageWithFilters(String name, String category, Pageable pageable);
    
    Optional<ProductModel> findById(Long id);
}
