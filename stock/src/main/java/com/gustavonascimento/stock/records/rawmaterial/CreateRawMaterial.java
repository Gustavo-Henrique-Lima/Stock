package com.gustavonascimento.stock.records.rawmaterial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateRawMaterial(

        @NotBlank
        @Size(max = 50)
        String code,

        @NotBlank
        @Size(max = 255)
        String name,

        @NotNull
        @PositiveOrZero
        Double stockQuantity
) {}