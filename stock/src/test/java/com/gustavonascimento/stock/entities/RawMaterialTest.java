package com.gustavonascimento.stock.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RawMaterialTest {

    @Test
    void shouldCreateRawMaterialUsingConstructor() {
        RawMaterial rm = new RawMaterial("RM-001", "Steel", 100.0);

        assertThat(rm.getCode()).isEqualTo("RM-001");
        assertThat(rm.getName()).isEqualTo("Steel");
        assertThat(rm.getStockQuantity()).isEqualTo(100.0);
    }

    @Test
    void shouldAllowUpdatingFieldsUsingSetters() {
        RawMaterial rm = new RawMaterial();

        rm.setCode("RM-002");
        rm.setName("Aluminum");
        rm.setStockQuantity(50.0);

        assertThat(rm.getCode()).isEqualTo("RM-002");
        assertThat(rm.getName()).isEqualTo("Aluminum");
        assertThat(rm.getStockQuantity()).isEqualTo(50.0);
    }

    @Test
    void shouldBeEqualWhenCodesAreEqual() {
        RawMaterial rm1 = new RawMaterial("RM-003", "Copper", 10.0);
        RawMaterial rm2 = new RawMaterial("RM-003", "Copper X", 999.0);

        assertThat(rm1)
                .isEqualTo(rm2)
                .hasSameHashCodeAs(rm2);
    }

    @Test
    void shouldNotBeEqualWhenCodesAreDifferent() {
        RawMaterial rm1 = new RawMaterial("RM-004", "Iron", 10.0);
        RawMaterial rm2 = new RawMaterial("RM-005", "Iron", 10.0);

        assertThat(rm1).isNotEqualTo(rm2);
    }

    @Test
    void shouldNotBeEqualToNull() {
        RawMaterial rm = new RawMaterial("RM-006", "Nickel", 5.0);

        assertThat(rm).isNotEqualTo(null);
    }

    @Test
    void shouldNotBeEqualToDifferentClass() {
        RawMaterial rm = new RawMaterial("RM-007", "Zinc", 3.0);

        assertThat(rm).isNotEqualTo("RM-007");
    }

    @Test
    void shouldBeEqualToItself() {
        RawMaterial rm = new RawMaterial("RM-008", "Tin", 7.0);

        assertThat(rm).isEqualTo(rm);
    }
}