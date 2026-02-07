package com.gustavonascimento.stock.usecases.rawmaterial;

import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.GetRawMaterial;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetRawMaterialUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(GetRawMaterialUseCase.class);

    private final RawMaterialRepository repository;

    public GetRawMaterialUseCase(RawMaterialRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public GetRawMaterial execute(Long rawMaterialId) {
        LOG.info("Buscando matéria-prima: {}", rawMaterialId);
        RawMaterial entity = repository.findById(rawMaterialId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Matéria-prima não encontrada")
                );
        LOG.info("Matéria-prima localizada com sucesso");
        return new GetRawMaterial(entity.getId(), entity.getCode(), entity.getName(), entity.getStockQuantity());
    }

}