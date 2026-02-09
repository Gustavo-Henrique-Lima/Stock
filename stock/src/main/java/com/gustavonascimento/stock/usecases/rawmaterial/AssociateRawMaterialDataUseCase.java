package com.gustavonascimento.stock.usecases.rawmaterial;

import com.gustavonascimento.stock.records.rawmaterial.AssociateRawMaterial;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociateRawMaterialDataUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(AssociateRawMaterialDataUseCase.class);

    private final RawMaterialRepository repository;

    public AssociateRawMaterialDataUseCase(RawMaterialRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<AssociateRawMaterial> execute(){
        return repository.findAllSummaries();
    }
}
