package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.records.product.GetProduct;
import com.gustavonascimento.stock.repositories.ProductRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;
import com.gustavonascimento.stock.usecases.product.GetProductUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private GetProductUseCase useCase;

    @Test
    void shouldReturnProductWhenIdExists() {
        Product entity = new Product(
                1L,
                "PR-001",
                "Product Test",
                BigDecimal.valueOf(299.90)
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(entity));

        GetProduct result = useCase.execute(1L);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.code()).isEqualTo("PR-001");
        assertThat(result.name()).isEqualTo("Product Test");
        assertThat(result.price()).isEqualTo(BigDecimal.valueOf(299.90));

        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Produto não encontrado");

        verify(repository).findById(999L);
        verifyNoMoreInteractions(repository);
    }
}