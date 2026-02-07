package com.gustavonascimento.stock.records.rawmaterial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateRawMaterial(

        @NotBlank
        @Size(max = 255)
        String name,

        @NotNull
        @PositiveOrZero
        Double stockQuantity
) {}