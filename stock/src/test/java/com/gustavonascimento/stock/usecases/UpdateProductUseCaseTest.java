package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.records.product.GetProduct;
import com.gustavonascimento.stock.records.product.UpdateProduct;
import com.gustavonascimento.stock.repositories.ProductRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;
import com.gustavonascimento.stock.usecases.product.UpdateProductUseCase;

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
class UpdateProductUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private UpdateProductUseCase useCase;

    @Test
    void shouldUpdateProductWhenIdExists() {
        Product entity = new Product(
                1L,
                "PR-001",
                "Old Name",
                BigDecimal.valueOf(100)
        );

        UpdateProduct input =
                new UpdateProduct("New Name", BigDecimal.valueOf(150));

        when(repository.findById(1L))
                .thenReturn(Optional.of(entity));

        when(repository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        GetProduct result = useCase.execute(1L, input);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.code()).isEqualTo("PR-001");
        assertThat(result.name()).isEqualTo("New Name");
        assertThat(result.price()).isEqualTo(BigDecimal.valueOf(150));

        verify(repository).findById(1L);
        verify(repository).save(entity);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        UpdateProduct input =
                new UpdateProduct("Any Name", BigDecimal.valueOf(50));

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(999L, input))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Produto não encontrada");

        verify(repository).findById(999L);
        verify(repository, never()).save(any());
        verifyNoMoreInteractions(repository);
    }
}