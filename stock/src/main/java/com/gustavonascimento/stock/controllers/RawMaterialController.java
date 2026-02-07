package com.gustavonascimento.stock.controllers;

import com.gustavonascimento.stock.controllers.exceptions.StandardError;
import com.gustavonascimento.stock.records.rawmaterial.CreateRawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.GetRawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.UpdateRawMaterial;
import com.gustavonascimento.stock.usecases.rawmaterial.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/raw-materials")
public class RawMaterialController {

    private final CreateRawMaterialUseCase createUseCase;
    private final UpdateRawMaterialUseCase updateUseCase;
    private final GetRawMaterialUseCase getUseCase;
    private final ListRawMaterialsUseCase listUseCase;
    private final DeleteRawMaterialUseCase deleteUseCase;

    public RawMaterialController(
            CreateRawMaterialUseCase createUseCase,
            UpdateRawMaterialUseCase updateUseCase,
            GetRawMaterialUseCase getUseCase,
            ListRawMaterialsUseCase listUseCase,
            DeleteRawMaterialUseCase deleteUseCase
    ) {
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @Operation(
            summary = "Create a raw material",
            description = "This endpoint is used to create a new raw material",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Raw material created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetRawMaterial.class)
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
                    @ApiResponse(description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GetRawMaterial> create(
            @RequestBody @Valid CreateRawMaterial record
    ) {
        GetRawMaterial rawMaterial = createUseCase.execute(record);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rawMaterial.id()).toUri();
        return ResponseEntity.created(uri).body(rawMaterial);
    }

    @Operation(
            summary = "Update a raw material",
            description = "This endpoint is used to update an existing raw material",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Raw material updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetRawMaterial.class)
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
                    @ApiResponse(description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GetRawMaterial> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRawMaterial record
    ) {
        GetRawMaterial recordUpdated = updateUseCase.execute(id, record);

        return ResponseEntity.ok(
                recordUpdated
        );
    }

    @Operation(
            summary = "Get raw material by id",
            description = "Returns a raw material by its id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetRawMaterial.class)
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
                    @ApiResponse(description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<GetRawMaterial> getById(@PathVariable Long id) {

        GetRawMaterial record = getUseCase.execute(id);

        return ResponseEntity.ok(
                record
        );
    }

    @Operation(
            summary = "List all raw materials paginated",
            description = "Returns a page of raw materials registered in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetRawMaterial.class)
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
    public ResponseEntity<Page<GetRawMaterial>> list(Pageable pageable) {

        Page<GetRawMaterial> response =
                listUseCase.execute(pageable);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete a raw material",
            description = "Deletes a raw material by its id",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Raw material deleted successfully"
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
                    @ApiResponse(description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
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
