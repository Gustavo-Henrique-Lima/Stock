package com.gustavonascimento.stock.usecases.rawmaterial;

import com.gustavonascimento.stock.records.rawmaterial.GetRawMaterial;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;
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
    public List<GetRawMaterial> execute() {
        LOG.info("Buscando lista de todas as máterias-primas cadastradas no DB");
        return repository.findAll()
                .stream()
                .map(entity -> new GetRawMaterial(
                        entity.getId(),
                        entity.getCode(),
                        entity.getName(),
                        entity.getStockQuantity()
                ))
                .collect(Collectors.toList());
    }
}