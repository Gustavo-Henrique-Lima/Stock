package com.gustavonascimento.stock.usecases.validation;

import com.gustavonascimento.stock.controllers.exceptions.FieldMessage;
import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.CreateRawMaterial;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RawMaterialInsertValidator implements ConstraintValidator<RawMaterialInsertValid, CreateRawMaterial> {

    private final RawMaterialRepository repository;

    public RawMaterialInsertValidator(RawMaterialRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(RawMaterialInsertValid ann) {
        /* Empty initialize */
    }

    @Override
    public boolean isValid(CreateRawMaterial record, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();
        Optional<RawMaterial> validCode = repository.findByCode(record.code());
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
