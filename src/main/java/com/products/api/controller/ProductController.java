package com.products.api.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.products.api.model.product.ProductModel;
import com.products.api.service.interfaces.IProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductModel>> findPageWithFilters(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String category,
        Pageable pageable
    ) {
        return ResponseEntity.ok(productService.findPageWithFilters(name, category, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> findById(@PathVariable Long id) {
        return productService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

}
