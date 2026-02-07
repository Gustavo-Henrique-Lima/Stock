package com.gustavonascimento.stock.records.rawmaterial;

import java.math.BigDecimal;

public record GetRawMaterial(
        Long id,
        String code,
        String name,
        Double stockQuantity
) {
}
