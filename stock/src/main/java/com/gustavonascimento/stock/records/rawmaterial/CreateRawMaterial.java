package com.gustavonascimento.stock.records.rawmaterial;

import com.gustavonascimento.stock.usecases.validation.RawMaterialInsertValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@RawMaterialInsertValid
public record CreateRawMaterial(

        @NotBlank(message = "O campo código é obrigatório")
        @Size(min = 3, max = 50, message = "O campo código deve conter entre 3 e 20 caracteres")
        String code,

        @NotBlank(message = "O campo nome é obrigatório")
        @Size(min = 3, max = 255, message = "O campo nome deve conter entre 3 e 255 caracteres")
        String name,

        @NotNull(message = "O campo quantidade do estoque é obrigatório")
        @PositiveOrZero(message = "O valor da quantidade de ser maior ou igual a 0")
        Double stockQuantity
) {}