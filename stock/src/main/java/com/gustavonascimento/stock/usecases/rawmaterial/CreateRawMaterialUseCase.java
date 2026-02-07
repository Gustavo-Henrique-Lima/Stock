package com.gustavonascimento.stock.usecases.rawmaterial;

import com.gustavonascimento.stock.records.rawmaterial.GetRawMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.CreateRawMaterial;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateRawMaterialUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(CreateRawMaterialUseCase.class);

    private final RawMaterialRepository repository;

    public CreateRawMaterialUseCase(RawMaterialRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public GetRawMaterial execute(CreateRawMaterial record) {
        LOG.info("Iniciando processo de cadastro de uma nova matéria-prima");

        RawMaterial entity  = new RawMaterial();

        LOG.info("Inserindo matéria-prima no DB");
        createRawMaterial(entity, record);
        entity = repository.save(entity);

        GetRawMaterial recordSave = new GetRawMaterial(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getStockQuantity());

        LOG.info("Matéria-prima cadastrada com sucesso");
        return recordSave;
    }

    private void createRawMaterial(RawMaterial entity, CreateRawMaterial record){
        LOG.info("Convertendo record em entidade");
        entity.setCode(record.code());
        entity.setName(record.name());
        entity.setStockQuantity(record.stockQuantity());
        LOG.info("Record convertido com sucesso");
    }
}