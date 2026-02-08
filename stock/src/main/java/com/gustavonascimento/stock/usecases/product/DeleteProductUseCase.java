package com.gustavonascimento.stock.usecases.product;

import com.gustavonascimento.stock.repositories.ProductRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteProductUseCase.class);

    private final ProductRepository repository;

    public DeleteProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(Long productId) {
        LOG.info("Iniciando processo de deleção do produto: {}", productId);

        if (!repository.existsById(productId)) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }

        LOG.info("Deletando produto do DB");
        repository.deleteById(productId);
    }

}
