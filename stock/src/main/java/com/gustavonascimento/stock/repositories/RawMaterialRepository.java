package com.gustavonascimento.stock.repositories;

import com.gustavonascimento.stock.entities.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RawMaterialRepository extends JpaRepository<com.gustavonascimento.stock.entities.RawMaterial, Long> {

    Optional<RawMaterial> findByCode(String code);

    boolean existsByCode(String code);
}