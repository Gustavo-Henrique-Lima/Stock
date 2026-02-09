package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.repositories.RawMaterialRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;
import com.gustavonascimento.stock.usecases.rawmaterial.DeleteRawMaterialUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRawMaterialUseCaseTest {

    @Mock
    private RawMaterialRepository repository;

    @InjectMocks
    private DeleteRawMaterialUseCase useCase;

    @Test
    void shouldDeleteRawMaterialWhenIdExists() {
        Long rawMaterialId = 1L;
        when(repository.existsById(rawMaterialId)).thenReturn(true);

        useCase.execute(rawMaterialId);

        verify(repository).existsById(rawMaterialId);
        verify(repository).deleteById(rawMaterialId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenRawMaterialDoesNotExist() {
        Long rawMaterialId = 99L;
        when(repository.existsById(rawMaterialId)).thenReturn(false);

        assertThatThrownBy(() -> useCase.execute(rawMaterialId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Máteria-prima não encontrada");

        verify(repository).existsById(rawMaterialId);
        verify(repository, never()).deleteById(anyLong());
        verifyNoMoreInteractions(repository);
    }
}