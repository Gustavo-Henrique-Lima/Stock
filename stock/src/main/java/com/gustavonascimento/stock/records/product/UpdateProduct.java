package com.gustavonascimento.stock.records.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateProduct(

        @NotBlank(message = "O campo código é obrigatório")
        @Size(min = 3, max = 255, message = "O campo código deve conter entre 3 e 255 caracteres")
        String name,

        @NotNull(message = "O campo valor do estoque é obrigatório")
        @PositiveOrZero(message = "O valor deve ser maior ou igual a 0")
        BigDecimal price
) {}