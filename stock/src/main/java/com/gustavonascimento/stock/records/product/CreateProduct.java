package com.gustavonascimento.stock.records.product;

import com.gustavonascimento.stock.usecases.validation.ProductInsertValid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@ProductInsertValid
public record CreateProduct(

        @NotBlank(message = "O campo código é obrigatório")
        @Size(min = 3, max = 50, message = "O campo código deve conter entre 3 e 20 caracteres")
        String code,

        @NotBlank(message = "O campo código é obrigatório")
        @Size(min = 3, max = 255, message = "O campo código deve conter entre 3 e 255 caracteres")
        String name,

        @NotNull(message = "O campo valor do estoque é obrigatório")
        @PositiveOrZero(message = "O valor deve ser maior ou igual a 0")
        BigDecimal price
) {}