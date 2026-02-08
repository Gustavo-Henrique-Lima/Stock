package com.gustavonascimento.stock.controllers;

import com.gustavonascimento.stock.records.productrawmaterial.AddRawMaterialToProduct;
import com.gustavonascimento.stock.records.productrawmaterial.GetProductRawMaterial;
import com.gustavonascimento.stock.records.productrawmaterial.UpdateProductRawMaterial;
import com.gustavonascimento.stock.usecases.productrawmaterial.AddRawMaterialToProductUseCase;
import com.gustavonascimento.stock.usecases.productrawmaterial.ListProductRawMaterialsUseCase;
import com.gustavonascimento.stock.usecases.productrawmaterial.RemoveRawMaterialFromProductUseCase;
import com.gustavonascimento.stock.usecases.productrawmaterial.UpdateProductRawMaterialUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/raw-materials")
public class ProductRawMaterialController {

    private final AddRawMaterialToProductUseCase addUseCase;
    private final UpdateProductRawMaterialUseCase updateUseCase;
    private final RemoveRawMaterialFromProductUseCase removeUseCase;
    private final ListProductRawMaterialsUseCase listUseCase;

    public ProductRawMaterialController(
            AddRawMaterialToProductUseCase addUseCase,
            UpdateProductRawMaterialUseCase updateUseCase,
            RemoveRawMaterialFromProductUseCase removeUseCase,
            ListProductRawMaterialsUseCase listUseCase
    ) {
        this.addUseCase = addUseCase;
        this.updateUseCase = updateUseCase;
        this.removeUseCase = removeUseCase;
        this.listUseCase = listUseCase;
    }

    @Operation(
            summary = "Add raw material to product",
            description = "Associates a raw material with a product with the required quantity",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Raw material associated successfully"
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
    @PostMapping(value = "/{productId}")
    public ResponseEntity<Void> add(
            @PathVariable Long productId,
            @RequestBody @Valid AddRawMaterialToProduct command
    ) {
        addUseCase.execute(productId, command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Update raw material quantity",
            description = "Updates the required quantity of a raw material for a product",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Association updated successfully"
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
    @PutMapping("/{productId}/{rawMaterialId}")
    public ResponseEntity<Void> update(
            @PathVariable Long productId,
            @PathVariable Long rawMaterialId,
            @RequestBody @Valid UpdateProductRawMaterial command
    ) {
        updateUseCase.execute(productId, rawMaterialId, command);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "List product raw materials",
            description = "Lists all raw materials associated with a product",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProductRawMaterial.class)
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
    @GetMapping(value = "/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GetProductRawMaterial>> list(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                listUseCase.execute(productId)
        );
    }

    @Operation(
            summary = "Remove raw material from product",
            description = "Removes the association between a product and a raw material",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Association removed successfully"
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
    @DeleteMapping("/{productId}/{rawMaterialId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remove(
            @PathVariable Long productId,
            @PathVariable Long rawMaterialId
    ) {
        removeUseCase.execute(productId, rawMaterialId);

        return ResponseEntity.noContent().build();
    }
}