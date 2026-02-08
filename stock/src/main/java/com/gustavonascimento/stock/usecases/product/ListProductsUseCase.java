package com.gustavonascimento.stock.usecases.product;

import com.gustavonascimento.stock.records.product.GetProduct;
import com.gustavonascimento.stock.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListProductsUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(ListProductsUseCase.class);

    private final ProductRepository repository;

    public ListProductsUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Page<GetProduct> execute(Pageable pageable) {
        LOG.info(
                "Buscando produtos página={} tamanho={} ordenação={}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort()
        );

        return repository.findAll(pageable)
                .map(entity -> new GetProduct(
                        entity.getId(),
                        entity.getCode(),
                        entity.getName(),
                        entity.getPrice()
                ));
    }
}