package com.gustavonascimento.stock.controllers;

import com.gustavonascimento.stock.controllers.exceptions.StandardError;
import com.gustavonascimento.stock.records.product.CreateProduct;
import com.gustavonascimento.stock.records.product.GetProduct;
import com.gustavonascimento.stock.records.product.UpdateProduct;
import com.gustavonascimento.stock.usecases.product.CreateProductUseCase;
import com.gustavonascimento.stock.usecases.product.DeleteProductUseCase;
import com.gustavonascimento.stock.usecases.product.GetProductUseCase;
import com.gustavonascimento.stock.usecases.product.ListProductsUseCase;
import com.gustavonascimento.stock.usecases.product.UpdateProductUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final CreateProductUseCase createUseCase;
    private final UpdateProductUseCase updateUseCase;
    private final GetProductUseCase getUseCase;
    private final ListProductsUseCase listUseCase;
    private final DeleteProductUseCase deleteUseCase;

    public ProductController(
            CreateProductUseCase createUseCase,
            UpdateProductUseCase updateUseCase,
            GetProductUseCase getUseCase,
            ListProductsUseCase listUseCase,
            DeleteProductUseCase deleteUseCase
    ) {
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @Operation(
            summary = "Create a product",
            description = "Creates a new product",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Product created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProduct.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{ \"message\": \"Unauthorized\" }")
                            )
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{ \"message\": \"Forbidden\" }")
                            )
                    ),
                    @ApiResponse(
                            description = "Unprocessable Entity",
                            responseCode = "422",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    ),
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GetProduct> create(
            @RequestBody @Valid CreateProduct record
    ) {
        GetProduct product = createUseCase.execute(record);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.id()).toUri();
        return ResponseEntity.created(uri).body(product);
    }

    @Operation(
            summary = "Update a product",
            description = "Updates an existing product",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProduct.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{ \"message\": \"Unauthorized\" }")
                            )
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{ \"message\": \"Forbidden\" }")
                            )
                    ),
                    @ApiResponse(
                            description = "Product not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{ \"message\": \"Product not found\" }")
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GetProduct> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProduct command
    ) {
        GetProduct product = updateUseCase.execute(id, command);

        return ResponseEntity.ok(product);
    }

    @Operation(
            summary = "Get product by id",
            description = "Returns a product by its id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProduct.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{ \"message\": \"Unauthorized\" }")
                            )
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{ \"message\": \"Forbidden\" }")
                            )
                    ),
                    @ApiResponse(
                            description = "Product not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{ \"message\": \"Product not found\" }")
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<GetProduct> getById(@PathVariable Long id) {

        GetProduct product = getUseCase.execute(id);

        return ResponseEntity.ok(product);
    }

    @Operation(
            summary = "List products",
            description = "Returns a paginated list of products",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProduct.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{ \"message\": \"Unauthorized\" }")
                            )
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{ \"message\": \"Forbidden\" }")
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all")
    public ResponseEntity<Page<GetProduct>> list(Pageable pageable) {

        Page<GetProduct> response = listUseCase.execute(pageable);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete a product",
            description = "Deletes a product by its id",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Product deleted successfully"
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{ \"message\": \"Unauthorized\" }")
                            )
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{ \"message\": \"Forbidden\" }")
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
}