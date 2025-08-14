package com.products.api.repository.adapter;

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

import com.products.api.model.product.ProductModel;
import com.products.api.repository.adapter.out.IPruductJpaRepository;
import com.products.api.repository.entity.CategoryEntity;
import com.products.api.repository.entity.ImageEntity;
import com.products.api.repository.entity.AttributeEntity;
import com.products.api.repository.entity.ProductEntity;

@ExtendWith(MockitoExtension.class)
class IProductAdapterRepositoryTest {

    @Mock
    private IPruductJpaRepository productJpaRepository;

    @InjectMocks
    private ProductAdapterRepository productAdapterRepository;

    private ProductEntity testProductEntity;
    private CategoryEntity testCategoryEntity;
    private ImageEntity testImageEntity;
    private AttributeEntity testAttributeEntity;
    private ProductModel testProductModel;

    @BeforeEach
    void setUp() {

        testCategoryEntity = CategoryEntity.builder()
            .name("Test Category")
            .description("Test Description")
            .build();

        testImageEntity = ImageEntity.builder()
            .url("https://example.com/image.jpg")
            .build();

        testAttributeEntity = AttributeEntity.builder()
            .name("Test Attribute")
            .description("Test Description")
            .build();

        testProductEntity = ProductEntity.builder()
            .name("Test Product")
            .description("Test Description")
            .price(new BigDecimal("99.99"))
            .categories(Arrays.asList(testCategoryEntity))
            .images(Arrays.asList(testImageEntity))
            .attributes(Arrays.asList(testAttributeEntity))
            .build();
        testProductEntity.setId(1L);

        testProductModel = ProductModel.builder()
            .id(1L)
            .name("Test Product")
            .description("Test Description")
            .price(new BigDecimal("99.99"))
            .build();
    }

    @Test
    @DisplayName("Should return page of product models when findPageWithFilters is called successfully")
    void findPageWithFilters_ShouldReturnPageOfProductModels_WhenSuccessful() {
        // Arrange
        String name = "Test";
        String category = "Electronics";
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductEntity> productEntityPage = new PageImpl<>(Arrays.asList(testProductEntity), pageable, 1);
        
        when(productJpaRepository.findPageWithFilters(name, category, pageable))
            .thenReturn(productEntityPage);

        // Act
        Page<ProductModel> result = productAdapterRepository.findPageWithFilters(name, category, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        
        ProductModel resultProduct = result.getContent().get(0);
        assertEquals(testProductEntity.getId(), resultProduct.getId());
        assertEquals(testProductEntity.getName(), resultProduct.getName());
        assertEquals(testProductEntity.getDescription(), resultProduct.getDescription());
        assertEquals(testProductEntity.getPrice(), resultProduct.getPrice());
        
        verify(productJpaRepository).findPageWithFilters(name, category, pageable);
    }

    @Test
    @DisplayName("Should return empty page when no products found")
    void findPageWithFilters_ShouldReturnEmptyPage_WhenNoProductsFound() {
        // Arrange
        String name = "NonExistent";
        String category = "Electronics";
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductEntity> emptyPage = new PageImpl<>(Arrays.asList(), pageable, 0);
        
        when(productJpaRepository.findPageWithFilters(name, category, pageable))
            .thenReturn(emptyPage);

        // Act
        Page<ProductModel> result = productAdapterRepository.findPageWithFilters(name, category, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());
        
        verify(productJpaRepository).findPageWithFilters(name, category, pageable);
    }

    @Test
    @DisplayName("Should return product model when findById is called with existing ID")
    void findById_ShouldReturnProductModel_WhenProductExists() {
        // Arrange
        Long productId = 1L;
        when(productJpaRepository.findById(productId))
            .thenReturn(Optional.of(testProductEntity));

        // Act
        Optional<ProductModel> result = productAdapterRepository.findById(productId);

        // Assert
        assertTrue(result.isPresent());
        ProductModel resultProduct = result.get();
        assertEquals(testProductEntity.getId(), resultProduct.getId());
        assertEquals(testProductEntity.getName(), resultProduct.getName());
        assertEquals(testProductEntity.getDescription(), resultProduct.getDescription());
        assertEquals(testProductEntity.getPrice(), resultProduct.getPrice());
        
        verify(productJpaRepository).findById(productId);
    }

    @Test
    @DisplayName("Should return empty optional when product not found")
    void findById_ShouldReturnEmptyOptional_WhenProductNotFound() {
        // Arrange
        Long productId = 999L;
        when(productJpaRepository.findById(productId))
            .thenReturn(Optional.empty());

        // Act
        Optional<ProductModel> result = productAdapterRepository.findById(productId);

        // Assert
        assertFalse(result.isPresent());
        
        verify(productJpaRepository).findById(productId);
    }

    @Test
    @DisplayName("Should handle null parameters gracefully")
    void findPageWithFilters_ShouldHandleNullParameters_Gracefully() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductEntity> productEntityPage = new PageImpl<>(Arrays.asList(testProductEntity), pageable, 1);
        
        when(productJpaRepository.findPageWithFilters(null, null, pageable))
            .thenReturn(productEntityPage);

        // Act
        Page<ProductModel> result = productAdapterRepository.findPageWithFilters(null, null, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        
        verify(productJpaRepository).findPageWithFilters(null, null, pageable);
    }

    @Test
    @DisplayName("Should handle empty string parameters")
    void findPageWithFilters_ShouldHandleEmptyStringParameters() {
        // Arrange
        String name = "";
        String category = "";
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductEntity> productEntityPage = new PageImpl<>(Arrays.asList(testProductEntity), pageable, 1);
        
        when(productJpaRepository.findPageWithFilters(name, category, pageable))
            .thenReturn(productEntityPage);

        // Act
        Page<ProductModel> result = productAdapterRepository.findPageWithFilters(name, category, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        
        verify(productJpaRepository).findPageWithFilters(name, category, pageable);
    }

    @Test
    @DisplayName("Should handle pagination parameters correctly")
    void findPageWithFilters_ShouldHandlePaginationParameters_Correctly() {
        // Arrange
        String name = "Test";
        String category = "Electronics";
        Pageable pageable = PageRequest.of(1, 5); // Second page, 5 items per page
        Page<ProductEntity> productEntityPage = new PageImpl<>(Arrays.asList(testProductEntity), pageable, 10);
        
        when(productJpaRepository.findPageWithFilters(name, category, pageable))
            .thenReturn(productEntityPage);

        // Act
        Page<ProductModel> result = productAdapterRepository.findPageWithFilters(name, category, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(10, result.getTotalElements());
        assertEquals(1, result.getNumber());
        assertEquals(5, result.getSize());
        
        verify(productJpaRepository).findPageWithFilters(name, category, pageable);
    }

    @Test
    @DisplayName("Should handle product entity with null values")
    void findById_ShouldHandleProductEntity_WithNullValues() {
        // Arrange
        ProductEntity productWithNullValues = ProductEntity.builder()
            .name(null)
            .description(null)
            .price(null)
            .build();
        productWithNullValues.setId(2L);
            
        Long productId = 2L;
        when(productJpaRepository.findById(productId))
            .thenReturn(Optional.of(productWithNullValues));

        // Act
        Optional<ProductModel> result = productAdapterRepository.findById(productId);

        // Assert
        assertTrue(result.isPresent());
        ProductModel resultProduct = result.get();
        assertEquals(productWithNullValues.getId(), resultProduct.getId());
        assertNull(resultProduct.getName());
        assertNull(resultProduct.getDescription());
        assertNull(resultProduct.getPrice());
        
        verify(productJpaRepository).findById(productId);
    }

    @Test
    @DisplayName("Should handle product entity with empty strings")
    void findById_ShouldHandleProductEntity_WithEmptyStrings() {
        // Arrange
        ProductEntity productWithEmptyStrings = ProductEntity.builder()
            .name("")
            .description("")
            .price(new BigDecimal("0.00"))
            .build();
        productWithEmptyStrings.setId(3L);
            
        Long productId = 3L;
        when(productJpaRepository.findById(productId))
            .thenReturn(Optional.of(productWithEmptyStrings));

        // Act
        Optional<ProductModel> result = productAdapterRepository.findById(productId);

        // Assert
        assertTrue(result.isPresent());
        ProductModel resultProduct = result.get();
        assertEquals(productWithEmptyStrings.getId(), resultProduct.getId());
        assertEquals("", resultProduct.getName());
        assertEquals("", resultProduct.getDescription());
        assertEquals(new BigDecimal("0.00"), resultProduct.getPrice());
        
        verify(productJpaRepository).findById(productId);
    }
}
