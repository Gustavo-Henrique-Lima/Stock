package com.gustavonascimento.stock.usecases.rawmaterial;

import com.gustavonascimento.stock.records.rawmaterial.GetRawMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.UpdateRawMaterial;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateRawMaterialUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateRawMaterialUseCase.class);

    private final RawMaterialRepository repository;

    public UpdateRawMaterialUseCase(RawMaterialRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public GetRawMaterial execute(Long rawMaterialId, UpdateRawMaterial record) {
        LOG.info("Iniciando processo de atualização de dados da matéria-prima: {}", rawMaterialId);

        RawMaterial entity = repository.findById(rawMaterialId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Máteria prima não encontrada")
                );

        updateData(entity, record);

        LOG.info("Inserindo matéria-prima no DB");
        entity = repository.save(entity);

        LOG.info("Matéria-prima atualizada com sucesso");
        return new GetRawMaterial(entity.getId(), entity.getCode(), entity.getName(), entity.getStockQuantity());
    }

    private void updateData(RawMaterial entity, UpdateRawMaterial record){
        LOG.info("Atualizando nome e quantidade da matéria-prima");
        entity.setName(record.name());
        entity.setStockQuantity(record.stockQuantity());
        LOG.info("Matéria-prima atualizada com sucesso");
    }
}
