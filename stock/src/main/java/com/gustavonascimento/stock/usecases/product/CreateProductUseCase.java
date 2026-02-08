package com.gustavonascimento.stock.usecases.product;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.product.CreateProduct;
import com.gustavonascimento.stock.records.product.GetProduct;
import com.gustavonascimento.stock.records.rawmaterial.GetRawMaterial;
import com.gustavonascimento.stock.repositories.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateProductUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(CreateProductUseCase.class);

    private final ProductRepository repository;

    public CreateProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public GetProduct execute(CreateProduct record) {
        LOG.info("Iniciando processo de cadastro de um novo produto");

        Product entity  = new Product();

        LOG.info("Inserindo produto no DB");
        createProduct(entity, record);
        entity = repository.save(entity);

        GetProduct recordSave = new GetProduct(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getPrice());

        LOG.info("Produto cadastrado com sucesso");
        return recordSave;
    }

    private void createProduct(Product entity, CreateProduct record){
        LOG.info("Convertendo record em entidade");
        entity.setCode(record.code());
        entity.setName(record.name());
        entity.setPrice(record.price());
        LOG.info("Record convertido com sucesso");
    }
}