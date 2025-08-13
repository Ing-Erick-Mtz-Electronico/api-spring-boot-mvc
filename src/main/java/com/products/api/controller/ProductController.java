package com.products.api.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.products.api.config.openApi.dto.PageProductSchema;
import com.products.api.dto.product.ProductToListDto;
import com.products.api.dto.product.ProductWithDetailDto;
import com.products.api.exception.ErrorDetail;
import com.products.api.service.interfaces.IProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    @Operation(
        summary = "Obtener productos",
        description = "Obtiene una página de productos con filtros",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Productos obtenidos correctamente",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                        implementation = PageProductSchema.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorDetail.class)
                )
            ),
        }
    )
    @GetMapping
    public ResponseEntity<Page<ProductToListDto>> findPageWithFilters(
        @Parameter(description = "Nombre del producto", example = "Producto 1")
        @RequestParam(required = false) String name,

        @Parameter(description = "Categoría del producto", example = "Electrónica")
        @RequestParam(required = false) String category,

        @Parameter(description = "Opciones de paginación", required = false, example = "{\"page\": 0, \"size\": 10, \"sort\": \"name,asc\"}")
        Pageable pageable
    ) {
        return ResponseEntity.ok(productService.findPageWithFilters(name, category, pageable));
    }

    @Operation(
        summary = "Obtener producto por ID",
        description = "Obtiene un producto por su ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Producto obtenido correctamente",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                        implementation = ProductWithDetailDto.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Producto por ID no encontrado",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorDetail.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorDetail.class)
                )
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductWithDetailDto> findById(
        @Parameter(description = "ID del producto", example = "1")
        @PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

}
