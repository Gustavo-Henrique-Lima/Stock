package com.gustavonascimento.stock.usecases.product;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.records.product.GetProduct;
import com.gustavonascimento.stock.records.product.UpdateProduct;
import com.gustavonascimento.stock.repositories.ProductRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateProductUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateProductUseCase.class);

    private final ProductRepository repository;

    public UpdateProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public GetProduct execute(Long productId, UpdateProduct record) {
        LOG.info("Iniciando processo de atualização de dados do produto: {}", productId);

        Product entity = repository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produto não encontrada")
                );

        updateData(entity, record);

        LOG.info("Inserindo produto no DB");
        entity = repository.save(entity);

        LOG.info("Produto atualizado com sucesso");
        return new GetProduct(entity.getId(), entity.getCode(), entity.getName(), entity.getPrice());
    }

    private void updateData(Product entity, UpdateProduct record){
        LOG.info("Atualizando nome e valor do material");
        entity.setName(record.name());
        entity.setPrice(record.price());
        LOG.info("Produto atualizada com sucesso");
    }
}