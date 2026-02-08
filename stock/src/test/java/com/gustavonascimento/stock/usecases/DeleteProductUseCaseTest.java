package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.repositories.ProductRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;
import com.gustavonascimento.stock.usecases.product.DeleteProductUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteProductUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private DeleteProductUseCase useCase;

    @Test
    void shouldDeleteProductWhenExists() {
        Long productId = 1L;

        when(repository.existsById(productId)).thenReturn(true);

        useCase.execute(productId);

        verify(repository).existsById(productId);
        verify(repository).deleteById(productId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        Long productId = 999L;

        when(repository.existsById(productId)).thenReturn(false);

        assertThatThrownBy(() -> useCase.execute(productId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Produto não encontrado");

        verify(repository).existsById(productId);
        verify(repository, never()).deleteById(anyLong());
        verifyNoMoreInteractions(repository);
    }
}