package com.gustavonascimento.stock.repositories;

import com.gustavonascimento.stock.entities.RawMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class RawMaterialRepositoryIT {

    @Autowired
    private RawMaterialRepository repository;

    @Test
    void shouldSaveAndFindByCode() {
        RawMaterial rm = new RawMaterial("RM-001", "Steel", 100.0);

        repository.save(rm);

        Optional<RawMaterial> result = repository.findByCode("RM-001");

        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("RM-001");
        assertThat(result.get().getName()).isEqualTo("Steel");
        assertThat(result.get().getStockQuantity()).isEqualTo(100.0);
    }

    @Test
    void shouldReturnEmptyWhenCodeDoesNotExist() {
        Optional<RawMaterial> result = repository.findByCode("NOT_FOUND");

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnTrueWhenCodeExists() {
        RawMaterial rm = new RawMaterial("RM-002", "Aluminum", 50.0);
        repository.save(rm);

        boolean exists = repository.existsByCode("RM-002");

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseWhenCodeDoesNotExist() {
        boolean exists = repository.existsByCode("RM-999");

        assertThat(exists).isFalse();
    }
}