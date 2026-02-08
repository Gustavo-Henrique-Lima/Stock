package com.gustavonascimento.stock.usecases.validation;

import com.gustavonascimento.stock.controllers.exceptions.FieldMessage;
import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.product.CreateProduct;
import com.gustavonascimento.stock.records.rawmaterial.CreateRawMaterial;
import com.gustavonascimento.stock.repositories.ProductRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductInsertValidator implements ConstraintValidator<ProductInsertValid, CreateProduct> {

    private final ProductRepository repository;

    public ProductInsertValidator(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(ProductInsertValid ann) {
        /* Empty initialize */
    }

    @Override
    public boolean isValid(CreateProduct record, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();
        Optional<Product> validCode = repository.findByCode(record.code());
        if (!validCode.isEmpty()) {
            list.add(new FieldMessage("code", "Esse código já está cadastrado"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
