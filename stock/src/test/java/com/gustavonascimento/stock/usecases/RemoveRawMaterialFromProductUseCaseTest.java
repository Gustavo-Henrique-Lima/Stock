package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.repositories.ProductRawMaterialRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;
import com.gustavonascimento.stock.usecases.productrawmaterial.RemoveRawMaterialFromProductUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveRawMaterialFromProductUseCaseTest {

    @Mock
    private ProductRawMaterialRepository repository;

    @InjectMocks
    private RemoveRawMaterialFromProductUseCase useCase;

    @Test
    void shouldRemoveAssociationWhenExists() {
        Long productId = 1L;
        Long rawMaterialId = 2L;

        when(repository.existsByProductIdAndRawMaterialId(productId, rawMaterialId))
                .thenReturn(true);

        useCase.execute(productId, rawMaterialId);

        verify(repository)
                .existsByProductIdAndRawMaterialId(productId, rawMaterialId);

        verify(repository)
                .deleteByProductIdAndRawMaterialId(productId, rawMaterialId);

        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenAssociationDoesNotExist() {
        Long productId = 1L;
        Long rawMaterialId = 99L;

        when(repository.existsByProductIdAndRawMaterialId(productId, rawMaterialId))
                .thenReturn(false);

        assertThatThrownBy(() -> useCase.execute(productId, rawMaterialId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Associação não encontrada.");

        verify(repository)
                .existsByProductIdAndRawMaterialId(productId, rawMaterialId);

        verify(repository, never())
                .deleteByProductIdAndRawMaterialId(anyLong(), anyLong());

        verifyNoMoreInteractions(repository);
    }
}