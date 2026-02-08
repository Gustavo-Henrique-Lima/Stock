package com.gustavonascimento.stock.controllers;

import com.gustavonascimento.stock.records.production.ProductionSimulationResult;
import com.gustavonascimento.stock.usecases.production.SimulateProductionUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/production")
public class ProductionSimulationController {

    private final SimulateProductionUseCase simulateProductionUseCase;

    public ProductionSimulationController(
            SimulateProductionUseCase simulateProductionUseCase
    ) {
        this.simulateProductionUseCase = simulateProductionUseCase;
    }

    @Operation(
            summary = "Simulate production",
            description = "Returns the list of products that can be produced with the available raw materials, prioritizing products with higher value",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Simulation executed successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductionSimulationResult.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{ \"message\": \"Unauthorized\" }"
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{ \"message\": \"Forbidden\" }"
                                    )
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/simulation")
    public ResponseEntity<ProductionSimulationResult> simulate() {

        ProductionSimulationResult result =
                simulateProductionUseCase.execute();

        return ResponseEntity.ok(result);
    }
}