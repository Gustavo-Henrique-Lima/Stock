package com.gustavonascimento.stock.records.production;

import java.math.BigDecimal;
import java.util.List;

public record ProductionSimulationResult(
        List<ProductionItem> items,
        BigDecimal totalProductionValue
) {}