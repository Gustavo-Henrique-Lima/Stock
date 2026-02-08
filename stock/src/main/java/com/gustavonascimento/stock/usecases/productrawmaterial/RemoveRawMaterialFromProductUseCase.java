package com.gustavonascimento.stock.usecases.productrawmaterial;

import com.gustavonascimento.stock.repositories.ProductRawMaterialRepository;

import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoveRawMaterialFromProductUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(RemoveRawMaterialFromProductUseCase.class);

    private final ProductRawMaterialRepository repository;

    public RemoveRawMaterialFromProductUseCase(ProductRawMaterialRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(Long productId, Long rawMaterialId) {
        LOG.info("Removendo associação entre produto: {} e matéria-prima: {}",productId, rawMaterialId);
        if (!repository.existsByProductIdAndRawMaterialId(productId, rawMaterialId)) {
            throw new ResourceNotFoundException("Associação não encontrada.");
        }

        repository.deleteByProductIdAndRawMaterialId(productId, rawMaterialId);
    }
}