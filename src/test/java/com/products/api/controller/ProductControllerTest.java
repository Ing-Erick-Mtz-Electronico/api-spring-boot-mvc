package com.products.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;

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
import org.springframework.http.ResponseEntity;

import com.products.api.dto.product.ProductToListDto;
import com.products.api.dto.product.ProductWithDetailDto;
import com.products.api.exception.GenericException;
import com.products.api.service.interfaces.IProductService;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private IProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductToListDto testProductToListDto;
    private ProductWithDetailDto testProductWithDetailDto;

    @BeforeEach
    void setUp() {
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
        Page<ProductToListDto> productPage = new PageImpl<>(Arrays.asList(testProductToListDto), pageable, 1);
        
        when(productService.findPageWithFilters(name, category, pageable))
            .thenReturn(productPage);

        // Act
        ResponseEntity<Page<ProductToListDto>> response = productController.findPageWithFilters(name, category, pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(testProductToListDto.getId(), response.getBody().getContent().get(0).getId());
        
        verify(productService).findPageWithFilters(name, category, pageable);
    }

    @Test
    @DisplayName("Should return empty page when no products found")
    void findPageWithFilters_ShouldReturnEmptyPage_WhenNoProductsFound() {
        // Arrange
        String name = "NonExistent";
        String category = "Electronics";
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductToListDto> emptyPage = new PageImpl<>(Arrays.asList(), pageable, 0);
        
        when(productService.findPageWithFilters(name, category, pageable))
            .thenReturn(emptyPage);

        // Act
        ResponseEntity<Page<ProductToListDto>> response = productController.findPageWithFilters(name, category, pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getTotalElements());
        assertTrue(response.getBody().getContent().isEmpty());
        
        verify(productService).findPageWithFilters(name, category, pageable);
    }

    @Test
    @DisplayName("Should handle null parameters gracefully")
    void findPageWithFilters_ShouldHandleNullParameters_Gracefully() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductToListDto> productPage = new PageImpl<>(Arrays.asList(testProductToListDto), pageable, 1);
        
        when(productService.findPageWithFilters(null, null, pageable))
            .thenReturn(productPage);

        // Act
        ResponseEntity<Page<ProductToListDto>> response = productController.findPageWithFilters(null, null, pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        
        verify(productService).findPageWithFilters(null, null, pageable);
    }

    @Test
    @DisplayName("Should return product when findById is called with existing ID")
    void findById_ShouldReturnProduct_WhenProductExists() {
        // Arrange
        Long productId = 1L;
        when(productService.findById(productId))
            .thenReturn(testProductWithDetailDto);

        // Act
        ResponseEntity<ProductWithDetailDto> response = productController.findById(productId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testProductWithDetailDto.getId(), response.getBody().getId());
        assertEquals(testProductWithDetailDto.getName(), response.getBody().getName());
        assertEquals(testProductWithDetailDto.getDescription(), response.getBody().getDescription());
        assertEquals(testProductWithDetailDto.getPrice(), response.getBody().getPrice());
        
        verify(productService).findById(productId);
    }

    @Test
    @DisplayName("Should propagate GenericException when service throws it")
    void findById_ShouldPropagateGenericException_WhenServiceThrowsIt() {
        // Arrange
        Long productId = 999L;
        GenericException expectedException = new GenericException("Product not found", HttpStatus.NOT_FOUND);
        
        when(productService.findById(productId))
            .thenThrow(expectedException);

        // Act & Assert
        GenericException exception = assertThrows(GenericException.class, () -> {
            productController.findById(productId);
        });
        
        assertEquals(expectedException.getMessage(), exception.getMessage());
        assertEquals(expectedException.getStatus(), exception.getStatus());
        
        verify(productService).findById(productId);
    }

    @Test
    @DisplayName("Should handle pagination parameters correctly")
    void findPageWithFilters_ShouldHandlePaginationParameters_Correctly() {
        // Arrange
        String name = "Test";
        String category = "Electronics";
        Pageable pageable = PageRequest.of(1, 5); // Second page, 5 items per page
        Page<ProductToListDto> productPage = new PageImpl<>(Arrays.asList(testProductToListDto), pageable, 10);
        
        when(productService.findPageWithFilters(name, category, pageable))
            .thenReturn(productPage);

        // Act
        ResponseEntity<Page<ProductToListDto>> response = productController.findPageWithFilters(name, category, pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(10, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getNumber());
        assertEquals(5, response.getBody().getSize());
        
        verify(productService).findPageWithFilters(name, category, pageable);
    }

    @Test
    @DisplayName("Should handle empty string parameters")
    void findPageWithFilters_ShouldHandleEmptyStringParameters() {
        // Arrange
        String name = "";
        String category = "";
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductToListDto> productPage = new PageImpl<>(Arrays.asList(testProductToListDto), pageable, 1);
        
        when(productService.findPageWithFilters(name, category, pageable))
            .thenReturn(productPage);

        // Act
        ResponseEntity<Page<ProductToListDto>> response = productController.findPageWithFilters(name, category, pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        
        verify(productService).findPageWithFilters(name, category, pageable);
    }
}
