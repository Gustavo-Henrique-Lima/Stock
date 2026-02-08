package com.gustavonascimento.stock.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void shouldCreateProductUsingConstructor() {
        Product product = new Product(
                1L,
                "PR-001",
                "Battery",
                BigDecimal.valueOf(199.90)
        );

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getCode()).isEqualTo("PR-001");
        assertThat(product.getName()).isEqualTo("Battery");
        assertThat(product.getPrice()).isEqualByComparingTo("199.90");
    }

    @Test
    void shouldCreateEmptyProductUsingDefaultConstructor() {
        Product product = new Product();

        assertThat(product).isNotNull();
        assertThat(product.getRawMaterials()).isNotNull();
        assertThat(product.getRawMaterials()).isEmpty();
    }

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        Product product = new Product();

        product.setCode("PR-002");
        product.setName("Inverter");
        product.setPrice(BigDecimal.valueOf(350.00));

        assertThat(product.getCode()).isEqualTo("PR-002");
        assertThat(product.getName()).isEqualTo("Inverter");
        assertThat(product.getPrice()).isEqualByComparingTo("350.00");
    }

    @Test
    void shouldInitializeRawMaterialsCollection() {
        Product product = new Product();

        assertThat(product.getRawMaterials())
                .isNotNull()
                .isEmpty();
    }

    @Test
    void shouldBeEqualWhenCodesAreTheSame() {
        Product product1 = new Product(
                1L,
                "PR-003",
                "Product A",
                BigDecimal.valueOf(100.00)
        );

        Product product2 = new Product(
                2L,
                "PR-003",
                "Product B",
                BigDecimal.valueOf(200.00)
        );

        assertThat(product1)
                .isEqualTo(product2)
                .hasSameHashCodeAs(product2);
    }

    @Test
    void shouldNotBeEqualWhenCodesAreDifferent() {
        Product product1 = new Product(
                1L,
                "PR-004",
                "Product A",
                BigDecimal.valueOf(100.00)
        );

        Product product2 = new Product(
                2L,
                "PR-005",
                "Product B",
                BigDecimal.valueOf(100.00)
        );

        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void shouldNotBeEqualToNullOrDifferentClass() {
        Product product = new Product(
                1L,
                "PR-006",
                "Product",
                BigDecimal.valueOf(50.00)
        );

        assertThat(product)
                .isNotEqualTo(null)
                .isNotEqualTo("PR-006");
    }
}