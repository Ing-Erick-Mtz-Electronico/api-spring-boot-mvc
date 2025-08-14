package com.products.api.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.products.api.repository.port.IProductPortRepository;
import com.products.api.service.ProductService;
import com.products.api.service.interfaces.IProductService;

@Configuration
public class ProductBeanConfig {
    @Bean
    public IProductService productService(IProductPortRepository productPortRepository) {
        return new ProductService(productPortRepository);
    }
}
