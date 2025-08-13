package com.products.api.service.interfaces;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.products.api.dto.product.ProductToListDto;
import com.products.api.dto.product.ProductWithDetailDto;

public interface IProductService {

    Page<ProductToListDto> findPageWithFilters(String name, String category, Pageable pageable);

    Optional<ProductWithDetailDto> findById(Long id);
}
