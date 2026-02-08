package com.gustavonascimento.stock.records.productrawmaterial;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.PositiveOrZero;

public record AddRawMaterialToProduct(

        @NotNull
        Long rawMaterialId,

        @NotNull(message = "O campo quantidade é obrigatório")
        @PositiveOrZero(message = "O valor deve ser maior ou igual a 0")
        Double requiredQuantity
) {}