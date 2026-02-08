package com.gustavonascimento.stock.records.rawmaterial;

public record GetRawMaterial(
        Long id,
        String code,
        String name,
        Double stockQuantity
) {
}
