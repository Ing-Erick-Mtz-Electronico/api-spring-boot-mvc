package com.products.api.controller;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.products.api.model.product.CategoryModel;
import com.products.api.model.product.ImageModel;
import com.products.api.constant.error.ErrorConstant;
import com.products.api.model.product.AttributeModel;
import com.products.api.model.product.ProductModel;
import com.products.api.repository.port.IProductPortRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductPortRepository productPortRepository;

    private ProductModel testProductModel;
    private CategoryModel testCategoryModel;
    private ImageModel testImageModel;
    private AttributeModel testAttributeModel;

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
    }

    @Test
    @DisplayName("Should return page of products when findPageWithFilters is called successfully")
    void findPageWithFilters_ShouldReturnPageOfProducts_WhenSuccessful() throws Exception {
        String name = "Test";
        String category = "Electronics";

        when(productPortRepository.findPageWithFilters(anyString(), anyString(), any(Pageable.class)))
            .thenReturn(new PageImpl<>(Arrays.asList(testProductModel), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/products")
            .param("name", name)
            .param("category", category))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value(testProductModel.getId()))
            .andExpect(jsonPath("$.content[0].name").value(testProductModel.getName()));
    }

    @Test
    @DisplayName("Should return empty page when no products found")
    void findPageWithFilters_ShouldReturnEmptyPage_WhenNoProductsFound() throws Exception {

        when(productPortRepository.findPageWithFilters(
            nullable(String.class), 
            nullable(String.class), 
            any(Pageable.class)))
            .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 10), 0));

        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    @DisplayName("Should return product when findById is called with existing ID")
    void findProductById_ShouldReturnProduct_WhenProductExists() throws Exception {
        when(productPortRepository.findById(anyLong()))
            .thenReturn(Optional.of(testProductModel));

        mockMvc.perform(get("/products/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(testProductModel.getId()))
            .andExpect(jsonPath("$.name").value(testProductModel.getName()));
    }

    @Test
    @DisplayName("Should return not found when product not found")
    void findProductById_ShouldReturnNotFound_WhenProductNotFound() throws Exception {

        when(productPortRepository.findById(anyLong()))
            .thenReturn(Optional.empty());

        mockMvc.perform(get("/products/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value(ErrorConstant.PRODUCT_NOT_FOUND));
    }

    @Test
    @DisplayName("Should throw GenericException when product not found")
    void findById_ShouldThrowInternalServerErrorException_WhenProductNotFound() throws Exception {

        when(productPortRepository.findById(anyLong()))
            .thenThrow(new RuntimeException(ErrorConstant.INTERNAL_SERVER_ERROR));
        
        mockMvc.perform(get("/products/1"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.message").value(ErrorConstant.INTERNAL_SERVER_ERROR));
    }
}
