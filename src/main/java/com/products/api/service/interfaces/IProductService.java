package com.products.api.service.interfaces;

import com.products.api.model.product.ProductModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface IProductService {

    List<ProductModel> findPageWithFilters(String name, String category, Pageable pageable);

    Optional<ProductModel> findById(Long id);
}
