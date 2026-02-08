package com.gustavonascimento.stock.records.productrawmaterial;

public record GetProductRawMaterial(

        Long rawMaterialId,
        String rawMaterialCode,
        String rawMaterialName,
        Double requiredQuantity
) {}