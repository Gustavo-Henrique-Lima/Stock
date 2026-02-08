package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.entities.ProductRawMaterial;
import com.gustavonascimento.stock.records.productrawmaterial.UpdateProductRawMaterial;
import com.gustavonascimento.stock.repositories.ProductRawMaterialRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;
import com.gustavonascimento.stock.usecases.productrawmaterial.UpdateProductRawMaterialUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductRawMaterialUseCaseTest {

    @Mock
    private ProductRawMaterialRepository repository;

    @InjectMocks
    private UpdateProductRawMaterialUseCase useCase;

    @Test
    void shouldUpdateRequiredQuantityWhenAssociationExists() {
        Long productId = 1L;
        Long rawMaterialId = 2L;

        ProductRawMaterial association = mock(ProductRawMaterial.class);

        when(repository.findByProductIdAndRawMaterialId(productId, rawMaterialId))
                .thenReturn(Optional.of(association));

        UpdateProductRawMaterial record =
                new UpdateProductRawMaterial(25.0);

        useCase.execute(productId, rawMaterialId, record);

        verify(repository)
                .findByProductIdAndRawMaterialId(productId, rawMaterialId);

        verify(association)
                .setRequiredQuantity(25.0);

        verifyNoMoreInteractions(repository, association);
    }

    @Test
    void shouldThrowExceptionWhenAssociationDoesNotExist() {
        Long productId = 1L;
        Long rawMaterialId = 99L;

        when(repository.findByProductIdAndRawMaterialId(productId, rawMaterialId))
                .thenReturn(Optional.empty());

        UpdateProductRawMaterial record =
                new UpdateProductRawMaterial(10.0);

        assertThatThrownBy(() ->
                useCase.execute(productId, rawMaterialId, record)
        )
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Associação não encontrada.");

        verify(repository)
                .findByProductIdAndRawMaterialId(productId, rawMaterialId);

        verifyNoMoreInteractions(repository);
    }
}