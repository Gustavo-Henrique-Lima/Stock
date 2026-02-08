package com.gustavonascimento.stock.usecases.productrawmaterial;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.entities.ProductRawMaterial;
import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.productrawmaterial.AddRawMaterialToProduct;
import com.gustavonascimento.stock.repositories.ProductRawMaterialRepository;
import com.gustavonascimento.stock.repositories.ProductRepository;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;
import com.gustavonascimento.stock.usecases.exceptions.ValidJunctionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddRawMaterialToProductUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(AddRawMaterialToProductUseCase.class);

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductRawMaterialRepository productRawMaterialRepository;

    public AddRawMaterialToProductUseCase(
            ProductRepository productRepository,
            RawMaterialRepository rawMaterialRepository,
            ProductRawMaterialRepository productRawMaterialRepository
    ) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.productRawMaterialRepository = productRawMaterialRepository;
    }

    @Transactional
    public void execute(Long productId, AddRawMaterialToProduct record) {
        LOG.info("Associando produto: {} a matéria prima: {}", productId, record.rawMaterialId());
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produto não encontrado.")
                );

        RawMaterial rawMaterial = rawMaterialRepository.findById(record.rawMaterialId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Matéria prima não encontrada.")
                );

        if (productRawMaterialRepository.existsByProductIdAndRawMaterialId(
                product.getId(),
                rawMaterial.getId()
        )) {
            throw new ValidJunctionException(
                    "A matéria prima já está associada a esse produto"
            );
        }

        ProductRawMaterial association = new ProductRawMaterial(
                null,
                product,
                rawMaterial,
                record.requiredQuantity()
        );
        LOG.info("Salvando associação no DB");
        productRawMaterialRepository.save(association);
    }
}