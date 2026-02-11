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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRawMaterialRepositoryTest {

    @Autowired
    private ProductRawMaterialRepository repository;

    @Autowired
    private EntityManager entityManager;

    private Product product;
    private RawMaterial rawMaterial;

    @BeforeEach
    void setUp() {
        product = new Product(
                null,
                "PR-001",
                "Produto de teste",
                BigDecimal.valueOf(100)
        );

        rawMaterial = new RawMaterial(
                "RM-001",
                "Cloro",
                100.0
        );

        entityManager.persist(product);
        entityManager.persist(rawMaterial);

        ProductRawMaterial prm = new ProductRawMaterial(
                null,
                product,
                rawMaterial,
                2.5
        );

        entityManager.persist(prm);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void shouldFindByProductIdAndRawMaterialId() {
        Optional<ProductRawMaterial> result =
                repository.findByProductIdAndRawMaterialId(
                        product.getId(),
                        rawMaterial.getId()
                );

        assertThat(result).isPresent();
        assertThat(result.get().getRequiredQuantity()).isEqualTo(2.5);
        assertThat(result.get().getProduct().getId()).isEqualTo(product.getId());
        assertThat(result.get().getRawMaterial().getId()).isEqualTo(rawMaterial.getId());
    }

    @Test
    void shouldReturnEmptyWhenCombinationDoesNotExist() {
        Optional<ProductRawMaterial> result =
                repository.findByProductIdAndRawMaterialId(
                        999L,
                        888L
                );

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnTrueWhenExistsByProductIdAndRawMaterialId() {
        boolean exists =
                repository.existsByProductIdAndRawMaterialId(
                        product.getId(),
                        rawMaterial.getId()
                );

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseWhenDoesNotExist() {
        boolean exists =
                repository.existsByProductIdAndRawMaterialId(
                        999L,
                        888L
                );

        assertThat(exists).isFalse();
    }

    @Test
    void shouldDeleteByProductIdAndRawMaterialId() {
        repository.deleteByProductIdAndRawMaterialId(
                product.getId(),
                rawMaterial.getId()
        );

        entityManager.flush();

        boolean exists =
                repository.existsByProductIdAndRawMaterialId(
                        product.getId(),
                        rawMaterial.getId()
                );

        assertThat(exists).isFalse();
    }
}