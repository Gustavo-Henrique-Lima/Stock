package com.gustavonascimento.stock.records.production;

import java.math.BigDecimal;

public record ProductionItem(
        Long productId,
        String productCode,
        String productName,
        BigDecimal unitPrice,
        long producibleQuantity,
        BigDecimal totalValue
) {}