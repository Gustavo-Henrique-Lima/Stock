package com.gustavonascimento.stock.usecases.rawmaterial;

import com.gustavonascimento.stock.records.rawmaterial.GetRawMaterial;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListRawMaterialsUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(ListRawMaterialsUseCase.class);

    private final RawMaterialRepository repository;

    public ListRawMaterialsUseCase(RawMaterialRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Page<GetRawMaterial> execute(Pageable pageable) {
        LOG.info(
                "Buscando matérias-primas página={} tamanho={} ordenação={}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort()
        );

        return repository.findAll(pageable)
                .map(entity -> new GetRawMaterial(
                        entity.getId(),
                        entity.getCode(),
                        entity.getName(),
                        entity.getStockQuantity()
                ));
    }
}