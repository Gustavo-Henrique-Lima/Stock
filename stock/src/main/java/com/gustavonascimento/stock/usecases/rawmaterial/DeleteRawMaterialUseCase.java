package com.gustavonascimento.stock.usecases.rawmaterial;

import com.gustavonascimento.stock.repositories.RawMaterialRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeleteRawMaterialUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteRawMaterialUseCase.class);

    private final RawMaterialRepository repository;

    public DeleteRawMaterialUseCase(RawMaterialRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(Long rawMaterialId) {
        LOG.info("Iniciando processo de deleção da máteria-prima: {}", rawMaterialId);

        if (!repository.existsById(rawMaterialId)) {
            throw new ResourceNotFoundException("Máteria-prima não encontrada");
        }

        LOG.info("Deletando matéria-prima do DB");
        repository.deleteById(rawMaterialId);
    }
}