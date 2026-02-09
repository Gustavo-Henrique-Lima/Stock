package com.gustavonascimento.stock.repositories;

import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.AssociateRawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {

    Optional<RawMaterial> findByCode(String code);

    boolean existsByCode(String code);

    @Query("""
    SELECT new com.gustavonascimento.stock.records.rawmaterial.AssociateRawMaterial(r.id, r.name)
    FROM RawMaterial r
    """)
    List<AssociateRawMaterial> findAllSummaries();

}