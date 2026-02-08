package com.gustavonascimento.stock.usecases.product;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.records.product.GetProduct;
import com.gustavonascimento.stock.repositories.ProductRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetProductUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(GetProductUseCase.class);

    private final ProductRepository repository;

    public GetProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public GetProduct execute(Long productId) {
        LOG.info("Buscando produto: {}", productId);

        Product entity = repository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produto não encontrado")
                );

        LOG.info("Produto localizada com sucesso");
        return new GetProduct(entity.getId(), entity.getCode(), entity.getName(), entity.getPrice());
    }
}
