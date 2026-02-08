package com.gustavonascimento.stock.usecases.productrawmaterial;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.records.productrawmaterial.GetProductRawMaterial;
import com.gustavonascimento.stock.repositories.ProductRepository;

import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListProductRawMaterialsUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(ListProductRawMaterialsUseCase.class);

    private final ProductRepository productRepository;

    public ListProductRawMaterialsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public List<GetProductRawMaterial> execute(Long productId) {
        LOG.info("Listando matéria-prima de um produto");
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produto não encontrado.")
                );

        return product.getRawMaterials()
                .stream()
                .map(prm -> new GetProductRawMaterial(
                        prm.getRawMaterial().getId(),
                        prm.getRawMaterial().getCode(),
                        prm.getRawMaterial().getName(),
                        prm.getRequiredQuantity()
                ))
                .toList();
    }
}