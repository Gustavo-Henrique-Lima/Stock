package com.gustavonascimento.stock.repositories;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.entities.ProductRawMaterial;
import com.gustavonascimento.stock.entities.RawMaterial;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private EntityManager entityManager;

    private Product productCheap;
    private Product productExpensive;

    @BeforeEach
    void setUp() {
        RawMaterial steel = new RawMaterial("RM-01", "Steel", 100.0);
        RawMaterial copper = new RawMaterial("RM-02", "Copper", 200.0);

        entityManager.persist(steel);
        entityManager.persist(copper);

        productCheap = new Product(
                null,
                "PR-BAIXO",
                "Produto barato",
                BigDecimal.valueOf(50)
        );

        productExpensive = new Product(
                null,
                "PR-CARO",
                "Produto caro",
                BigDecimal.valueOf(500)
        );

        entityManager.persist(productCheap);
        entityManager.persist(productExpensive);

        ProductRawMaterial prm1 = new ProductRawMaterial(
                null,
                productExpensive,
                steel,
                2.0
        );

        ProductRawMaterial prm2 = new ProductRawMaterial(
                null,
                productExpensive,
                copper,
                5.0
        );

        entityManager.persist(prm1);
        entityManager.persist(prm2);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void shouldFindByCodeWhenExists() {
        Optional<Product> result = repository.findByCode("PR-CARO");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Produto caro");
    }

    @Test
    void shouldReturnEmptyWhenCodeDoesNotExist() {
        Optional<Product> result = repository.findByCode("NAO-EXISTE");

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnTrueWhenExistsByCode() {
        boolean exists = repository.existsByCode("PR-BAIXO");

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseWhenCodeDoesNotExist() {
        boolean exists = repository.existsByCode("INVALIDO");

        assertThat(exists).isFalse();
    }

    @Test
    void shouldFindAllWithRawMaterialsOrderedByPriceDesc() {
        List<Product> result =
                repository.findAllWithRawMaterialsOrderByPriceDesc();

        assertThat(result)
                .hasSize(5)
                .extracting(Product::getCode)
                .containsExactly("PR-CARO", "P002", "PR-BAIXO", "P003", "P001");

        Product expensive = result.get(0);

        assertThat(expensive.getRawMaterials())
                .hasSize(2);

        expensive.getRawMaterials().forEach(prm -> {
            assertThat(prm.getRawMaterial()).isNotNull();
            assertThat(prm.getRawMaterial().getName()).isNotBlank();
        });
    }

    @Test
    void shouldLoadProductWithoutRawMaterials() {
        List<Product> result =
                repository.findAllWithRawMaterialsOrderByPriceDesc();

        Product cheap = result.stream()
                .filter(p -> p.getCode().equals("PR-BAIXO"))
                .findFirst()
                .orElseThrow();

        assertThat(cheap.getRawMaterials())
                .isNotNull()
                .isEmpty();
    }

}