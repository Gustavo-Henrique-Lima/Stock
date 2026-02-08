package com.gustavonascimento.stock.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRawMaterialTest {

    @Test
    void shouldBeEqualWhenRawMaterialAndQuantityAreTheSame() {
        RawMaterial rm = new RawMaterial("RM-01", "Steel", 100.0);

        ProductRawMaterial prm1 = new ProductRawMaterial(
                null, new Product(), rm, 2.0
        );

        ProductRawMaterial prm2 = new ProductRawMaterial(
                null, new Product(), rm, 2.0
        );

        assertThat(prm1)
                .isEqualTo(prm2)
                .hasSameHashCodeAs(prm2);
    }

    @Test
    void shouldNotBeEqualWhenRawMaterialIsDifferent() {
        RawMaterial rm1 = new RawMaterial("RM-01", "Steel", 100.0);
        RawMaterial rm2 = new RawMaterial("RM-02", "Copper", 100.0);

        ProductRawMaterial prm1 =
                new ProductRawMaterial(null, new Product(), rm1, 2.0);

        ProductRawMaterial prm2 =
                new ProductRawMaterial(null, new Product(), rm2, 2.0);

        assertThat(prm1).isNotEqualTo(prm2);
    }

    @Test
    void shouldNotBeEqualWhenQuantityIsDifferent() {
        RawMaterial rm = new RawMaterial("RM-01", "Steel", 100.0);

        ProductRawMaterial prm1 =
                new ProductRawMaterial(null, new Product(), rm, 2.0);

        ProductRawMaterial prm2 =
                new ProductRawMaterial(null, new Product(), rm, 5.0);

        assertThat(prm1).isNotEqualTo(prm2);
    }

    @Test
    void shouldNotBeEqualToNullOrDifferentClass() {
        ProductRawMaterial prm =
                new ProductRawMaterial(null, new Product(), new RawMaterial(), 1.0);

        assertThat(prm)
                .isNotEqualTo(null)
                .isNotEqualTo("ProductRawMaterial");
    }
}
