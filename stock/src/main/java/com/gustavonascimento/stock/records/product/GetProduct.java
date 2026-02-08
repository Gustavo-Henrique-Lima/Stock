package com.gustavonascimento.stock.records.product;

import java.math.BigDecimal;

public record GetProduct(
        Long id,
        String code,
        String name,
        BigDecimal price
) {}