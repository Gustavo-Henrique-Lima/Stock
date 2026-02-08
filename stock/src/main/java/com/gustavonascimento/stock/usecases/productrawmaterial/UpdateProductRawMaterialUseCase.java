package com.gustavonascimento.stock.usecases.productrawmaterial;

import com.gustavonascimento.stock.entities.ProductRawMaterial;
import com.gustavonascimento.stock.records.productrawmaterial.UpdateProductRawMaterial;
import com.gustavonascimento.stock.repositories.ProductRawMaterialRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateProductRawMaterialUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateProductRawMaterialUseCase.class);

    private final ProductRawMaterialRepository repository;

    public UpdateProductRawMaterialUseCase(ProductRawMaterialRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(
            Long productId,
            Long rawMaterialId,
            UpdateProductRawMaterial record
    ) {

        LOG.info("Atualizado quantidade necessária entre o produto: {} e a matéria-prima: {}", productId, rawMaterialId);
        ProductRawMaterial association =
                repository.findByProductIdAndRawMaterialId(productId, rawMaterialId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Associação não encontrada.")
                        );

        association.setRequiredQuantity(record.requiredQuantity());
    }
}