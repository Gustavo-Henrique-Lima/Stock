package com.gustavonascimento.stock.repositories;

import com.gustavonascimento.stock.entities.ProductRawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRawMaterialRepository extends JpaRepository<ProductRawMaterial, Long> {

    Optional<ProductRawMaterial> findByProductIdAndRawMaterialId(
            Long productId,
            Long rawMaterialId
    );

    boolean existsByProductIdAndRawMaterialId(
            Long productId,
            Long rawMaterialId
    );

    void deleteByProductIdAndRawMaterialId(
            Long productId,
            Long rawMaterialId
    );
}