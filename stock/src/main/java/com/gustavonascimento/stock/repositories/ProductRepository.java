package com.gustavonascimento.stock.repositories;

import com.gustavonascimento.stock.entities.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCode(String code);

    boolean existsByCode(String code);

    @Query("""
        SELECT DISTINCT p
        FROM Product p
        LEFT JOIN FETCH p.rawMaterials prm
        LEFT JOIN FETCH prm.rawMaterial rm
        ORDER BY p.price DESC
    """)
    List<Product> findAllWithRawMaterialsOrderByPriceDesc();

}
