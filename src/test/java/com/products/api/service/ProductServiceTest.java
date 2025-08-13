package com.products.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import com.products.api.builder.ProductDtoBuilder;
import com.products.api.constant.error.ErrorConstant;
import com.products.api.dto.product.ProductToListDto;
import com.products.api.dto.product.ProductWithDetailDto;
import com.products.api.exception.GenericException;
import com.products.api.model.product.CategoryModel;
import com.products.api.model.product.ImageModel;
import com.products.api.model.product.ProductModel;
import com.products.api.model.product.AttributeModel;
import com.products.api.repository.port.IProductPortRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private IProductPortRepository productPortRepository;

    @InjectMocks
    private ProductService productService;

    private ProductModel testProductModel;
    private CategoryModel testCategoryModel;
    private ImageModel testImageModel;
    private AttributeModel testAttributeModel;
    private ProductToListDto testProductToListDto;
    private ProductWithDetailDto testProductWithDetailDto;

    @BeforeEach
    void setUp() {

        testCategoryModel = CategoryModel.builder()
            .id(1L)
            .name("Test Category")
            .description("Test Description")
            .build();

        testImageModel = ImageModel.builder()
            .id(1L)
            .url("https://example.com/image.jpg")
            .build();

        testAttributeModel = AttributeModel.builder()
            .id(1L)
            .name("Test Attribute")
            .description("Test Description")
            .build();

        testProductModel = ProductModel.builder()
            .id(1L)
            .name("Test Product")
            .description("Test Description")
            .price(new BigDecimal("99.99"))
            .categories(Arrays.asList(testCategoryModel))
            .images(Arrays.asList(testImageModel))
            .attributes(Arrays.asList(testAttributeModel))
            .build();

        testProductToListDto = ProductToListDto.builder()
            .id(1L)
            .name("Test Product")
            .description("Test Description")
            .price(new BigDecimal("99.99"))
            .build();

        testProductWithDetailDto = ProductWithDetailDto.builder()
            .id(1L)
            .name("Test Product")
            .description("Test Description")
            .price(new BigDecimal("99.99"))
            .build();
    }

    @Test
    @DisplayName("Should return page of products when findPageWithFilters is called successfully")
    void findPageWithFilters_ShouldReturnPageOfProducts_WhenSuccessful() {
        // Arrange
        String name = "Test";
        String category = "Electronics";
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductModel> productPage = new PageImpl<>(Arrays.asList(testProductModel), pageable, 1);
        
        when(productPortRepository.findPageWithFilters(name, category, pageable))
            .thenReturn(productPage);

        // Act
        Page<ProductToListDto> result = productService.findPageWithFilters(name, category, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(testProductModel.getId(), result.getContent().get(0).getId());
        assertEquals(testProductModel.getName(), result.getContent().get(0).getName());
        
        verify(productPortRepository).findPageWithFilters(name, category, pageable);
    }

    @Test
    @DisplayName("Should return empty page when no products found")
    void findPageWithFilters_ShouldReturnEmptyPage_WhenNoProductsFound() {
        // Arrange
        String name = "NonExistent";
        String category = "Electronics";
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductModel> emptyPage = new PageImpl<>(Arrays.asList(), pageable, 0);
        
        when(productPortRepository.findPageWithFilters(name, category, pageable))
            .thenReturn(emptyPage);

        // Act
        Page<ProductToListDto> result = productService.findPageWithFilters(name, category, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());
        
        verify(productPortRepository).findPageWithFilters(name, category, pageable);
    }

    @Test
    @DisplayName("Should throw GenericException when repository throws exception")
    void findPageWithFilters_ShouldThrowGenericException_WhenRepositoryThrowsException() {
        // Arrange
        String name = "Test";
        String category = "Electronics";
        Pageable pageable = PageRequest.of(0, 10);
        
        when(productPortRepository.findPageWithFilters(name, category, pageable))
            .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        GenericException exception = assertThrows(GenericException.class, () -> {
            productService.findPageWithFilters(name, category, pageable);
        });
        
        assertEquals(ErrorConstant.INTERNAL_SERVER_ERROR, exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        
        verify(productPortRepository).findPageWithFilters(name, category, pageable);
    }

    @Test
    @DisplayName("Should return product when findById is called with existing ID")
    void findById_ShouldReturnProduct_WhenProductExists() {
        // Arrange
        Long productId = 1L;
        when(productPortRepository.findById(productId))
            .thenReturn(Optional.of(testProductModel));

        // Act
        ProductWithDetailDto result = productService.findById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(testProductModel.getId(), result.getId());
        assertEquals(testProductModel.getName(), result.getName());
        assertEquals(testProductModel.getDescription(), result.getDescription());
        assertEquals(testProductModel.getPrice(), result.getPrice());
        
        verify(productPortRepository).findById(productId);
    }

    @Test
    @DisplayName("Should throw GenericException when product not found")
    void findById_ShouldThrowGenericException_WhenProductNotFound() {
        // Arrange
        Long productId = 999L;
        when(productPortRepository.findById(productId))
            .thenReturn(Optional.empty());

        // Act & Assert
        GenericException exception = assertThrows(GenericException.class, () -> {
            productService.findById(productId);
        });
        
        assertEquals(ErrorConstant.PRODUCT_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        
        verify(productPortRepository).findById(productId);
    }

    @Test
    @DisplayName("Should handle null parameters gracefully")
    void findPageWithFilters_ShouldHandleNullParameters_Gracefully() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductModel> productPage = new PageImpl<>(Arrays.asList(testProductModel), pageable, 1);
        
        when(productPortRepository.findPageWithFilters(null, null, pageable))
            .thenReturn(productPage);

        // Act
        Page<ProductToListDto> result = productService.findPageWithFilters(null, null, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        
        verify(productPortRepository).findPageWithFilters(null, null, pageable);
    }

}
