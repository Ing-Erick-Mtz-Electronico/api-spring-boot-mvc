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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductService implements IProductService, ProductDtoBuilder {
    
    private final IProductPortRepository productPortRepository;

    public ProductService(IProductPortRepository productPortRepository) {
        this.productPortRepository = productPortRepository;
    }

    public Page<ProductToListDto> findPageWithFilters(String name, String category, Pageable pageable) {
        log.info("Buscando productos con filtros: name={}, category={}", name, category);

        try {
            return productPortRepository.findPageWithFilters(name, category, pageable)
                .map(this::toBasicProductDto);
        } catch (Exception e) {
            log.error("Error al buscar productos con filtros: name={}, category={}", name, category, e);
            throw new GenericException(ErrorConstant.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ProductWithDetailDto findById(Long id) {
        log.info("Buscando producto con ID: {}", id);
        
        try {
            return productPortRepository.findById(id)
                .map(this::toFullProductDto)
                .orElseThrow(() -> new GenericException(
                    ErrorConstant.PRODUCT_NOT_FOUND, 
                    HttpStatus.NOT_FOUND
                ));
        } catch (GenericException e) {
            log.warn("Producto no encontrado con ID: {}", id);
            throw e;
        }
    }
}
