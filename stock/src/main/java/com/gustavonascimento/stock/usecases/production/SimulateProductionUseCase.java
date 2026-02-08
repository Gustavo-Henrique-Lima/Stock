package com.gustavonascimento.stock.usecases.production;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.entities.ProductRawMaterial;
import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.production.ProductionItem;
import com.gustavonascimento.stock.records.production.ProductionSimulationResult;
import com.gustavonascimento.stock.repositories.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class SimulateProductionUseCase {

    private final ProductRepository productRepository;

    public SimulateProductionUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public ProductionSimulationResult execute() {

        // Buscar todos os produtos ordenados por maior preço
        List<Product> products =
                productRepository.findAllWithRawMaterialsOrderByPriceDesc();

        // Criar estoque
        Map<Long, BigDecimal> virtualStock = new HashMap<>();

        for (Product product : products) {
            for (ProductRawMaterial prm : product.getRawMaterials()) {
                RawMaterial rm = prm.getRawMaterial();
                virtualStock.putIfAbsent(
                        rm.getId(),
                        BigDecimal.valueOf(rm.getStockQuantity())
                );
            }
        }

        List<ProductionItem> items = new ArrayList<>();
        BigDecimal totalProductionValue = BigDecimal.ZERO;

        // Simulação
        for (Product product : products) {

            if (product.getRawMaterials().isEmpty()) {
                continue;
            }

            long maxProducible = Long.MAX_VALUE;

            for (ProductRawMaterial prm : product.getRawMaterials()) {

                BigDecimal available =
                        virtualStock.get(prm.getRawMaterial().getId());

                BigDecimal required =
                        BigDecimal.valueOf(prm.getRequiredQuantity());

                if (required.compareTo(BigDecimal.ZERO) <= 0) {
                    maxProducible = 0;
                    break;
                }

                long possible =
                        available.divide(required, 0, RoundingMode.FLOOR)
                                .longValue();

                maxProducible = Math.min(maxProducible, possible);
            }

            if (maxProducible <= 0) {
                continue;
            }

            // Consumo
            for (ProductRawMaterial prm : product.getRawMaterials()) {

                Long rmId = prm.getRawMaterial().getId();

                BigDecimal consumption =
                        BigDecimal.valueOf(prm.getRequiredQuantity())
                                .multiply(BigDecimal.valueOf(maxProducible));

                virtualStock.put(
                        rmId,
                        virtualStock.get(rmId).subtract(consumption)
                );
            }

            BigDecimal productTotalValue =
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(maxProducible));

            totalProductionValue =
                    totalProductionValue.add(productTotalValue);

            items.add(
                    new ProductionItem(
                            product.getId(),
                            product.getCode(),
                            product.getName(),
                            product.getPrice(),
                            maxProducible,
                            productTotalValue
                    )
            );
        }

        return new ProductionSimulationResult(items, totalProductionValue);
    }
}
