package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.entities.ProductRawMaterial;
import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.production.ProductionItem;
import com.gustavonascimento.stock.records.production.ProductionSimulationResult;
import com.gustavonascimento.stock.repositories.ProductRepository;
import com.gustavonascimento.stock.usecases.production.SimulateProductionUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimulateProductionUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private SimulateProductionUseCase useCase;

    @Test
    void shouldSimulateProductionWithSufficientStock() {
        RawMaterial steel = new RawMaterial("RM-01", "Steel", 100.0);
        setId(steel, 1L);

        Product product = new Product(
                1L,
                "PR-01",
                "Product A",
                BigDecimal.valueOf(10)
        );

        ProductRawMaterial prm =
                new ProductRawMaterial(null, product, steel, 2.0);

        product.getRawMaterials().add(prm);

        when(productRepository.findAllWithRawMaterialsOrderByPriceDesc())
                .thenReturn(List.of(product));

        ProductionSimulationResult result = useCase.execute();

        assertThat(result).isNotNull();
        assertThat(result.items()).hasSize(1);

        ProductionItem item = result.items().get(0);

        assertThat(item.productCode()).isEqualTo("PR-01");
        assertThat(item.totalValue()).isEqualTo(BigDecimal.valueOf(500));

        assertThat(result.totalProductionValue())
                .isEqualTo(BigDecimal.valueOf(500));
    }

    @Test
    void shouldPrioritizeMoreExpensiveProductWhenCompetingForStock() {
        RawMaterial steel = new RawMaterial("RM-01", "Steel", 10.0);
        setId(steel, 1L);

        Product expensive = new Product(
                1L,
                "PR-HIGH",
                "Expensive",
                BigDecimal.valueOf(100)
        );

        Product cheap = new Product(
                2L,
                "PR-LOW",
                "Cheap",
                BigDecimal.valueOf(10)
        );

        ProductRawMaterial prmHigh =
                new ProductRawMaterial(null, expensive, steel, 5.0);

        ProductRawMaterial prmLow =
                new ProductRawMaterial(null, cheap, steel, 5.0);

        expensive.getRawMaterials().add(prmHigh);
        cheap.getRawMaterials().add(prmLow);

        when(productRepository.findAllWithRawMaterialsOrderByPriceDesc())
                .thenReturn(List.of(expensive, cheap));

        ProductionSimulationResult result = useCase.execute();

        assertThat(result.items()).hasSize(1);

        ProductionItem item = result.items().get(0);

        assertThat(item.productCode()).isEqualTo("PR-HIGH");

        assertThat(result.totalProductionValue())
                .isEqualTo(BigDecimal.valueOf(200));
    }

    @Test
    void shouldIgnoreProductWhenRequiredQuantityIsZeroOrNegative() {
        RawMaterial steel = new RawMaterial("RM-01", "Steel", 100.0);
        setId(steel, 1L);

        Product product = new Product(
                1L,
                "PR-01",
                "Invalid Product",
                BigDecimal.valueOf(10)
        );

        ProductRawMaterial prm =
                new ProductRawMaterial(null, product, steel, 0.0);

        product.getRawMaterials().add(prm);

        when(productRepository.findAllWithRawMaterialsOrderByPriceDesc())
                .thenReturn(List.of(product));

        ProductionSimulationResult result = useCase.execute();

        assertThat(result.items()).isEmpty();
        assertThat(result.totalProductionValue()).isZero();
    }

    private static void setId(Object entity, Long id) {
        try {
            var field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}