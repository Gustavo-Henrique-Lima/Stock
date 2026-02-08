package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.entities.ProductRawMaterial;
import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.productrawmaterial.AddRawMaterialToProduct;
import com.gustavonascimento.stock.repositories.ProductRawMaterialRepository;
import com.gustavonascimento.stock.repositories.ProductRepository;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;
import com.gustavonascimento.stock.usecases.exceptions.ValidJunctionException;
import com.gustavonascimento.stock.usecases.productrawmaterial.AddRawMaterialToProductUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddRawMaterialToProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @Mock
    private ProductRawMaterialRepository productRawMaterialRepository;

    @InjectMocks
    private AddRawMaterialToProductUseCase useCase;

    @Test
    void shouldAddRawMaterialToProductSuccessfully() {
        Product product = new Product(
                1L, "PR-01", "Product", BigDecimal.valueOf(100)
        );

        RawMaterial rawMaterial =
                new RawMaterial("RM-01", "Steel", 100.0);
        setId(rawMaterial, 2L);

        AddRawMaterialToProduct input =
                new AddRawMaterialToProduct(2L, 5.0);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(rawMaterialRepository.findById(2L))
                .thenReturn(Optional.of(rawMaterial));

        when(productRawMaterialRepository
                .existsByProductIdAndRawMaterialId(1L, 2L))
                .thenReturn(false);

        useCase.execute(1L, input);

        ArgumentCaptor<ProductRawMaterial> captor =
                ArgumentCaptor.forClass(ProductRawMaterial.class);

        verify(productRawMaterialRepository).save(captor.capture());

        ProductRawMaterial saved = captor.getValue();

        assertThat(saved.getProduct()).isSameAs(product);
        assertThat(saved.getRawMaterial()).isSameAs(rawMaterial);
        assertThat(saved.getRequiredQuantity()).isEqualTo(5.0);

        verify(productRepository).findById(1L);
        verify(rawMaterialRepository).findById(2L);
        verify(productRawMaterialRepository)
                .existsByProductIdAndRawMaterialId(1L, 2L);
        verifyNoMoreInteractions(
                productRepository,
                rawMaterialRepository,
                productRawMaterialRepository
        );
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        AddRawMaterialToProduct input =
                new AddRawMaterialToProduct(2L, 5.0);

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(1L, input))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Produto não encontrado.");

        verify(productRepository).findById(1L);
        verifyNoMoreInteractions(
                productRepository,
                rawMaterialRepository,
                productRawMaterialRepository
        );
    }

    @Test
    void shouldThrowExceptionWhenRawMaterialDoesNotExist() {
        Product product = new Product(
                1L, "PR-01", "Product", BigDecimal.valueOf(100)
        );

        AddRawMaterialToProduct input =
                new AddRawMaterialToProduct(2L, 5.0);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(rawMaterialRepository.findById(2L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(1L, input))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Matéria prima não encontrada.");

        verify(productRepository).findById(1L);
        verify(rawMaterialRepository).findById(2L);
        verifyNoMoreInteractions(
                productRepository,
                rawMaterialRepository,
                productRawMaterialRepository
        );
    }

    @Test
    void shouldThrowExceptionWhenAssociationAlreadyExists() {
        Product product = new Product(
                1L, "PR-01", "Product", BigDecimal.valueOf(100)
        );

        RawMaterial rawMaterial =
                new RawMaterial("RM-01", "Steel", 100.0);
        setId(rawMaterial, 2L);

        AddRawMaterialToProduct input =
                new AddRawMaterialToProduct(2L, 5.0);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(rawMaterialRepository.findById(2L))
                .thenReturn(Optional.of(rawMaterial));

        when(productRawMaterialRepository
                .existsByProductIdAndRawMaterialId(1L, 2L))
                .thenReturn(true);

        assertThatThrownBy(() -> useCase.execute(1L, input))
                .isInstanceOf(ValidJunctionException.class)
                .hasMessage("A matéria prima já está associada a esse produto");

        verify(productRepository).findById(1L);
        verify(rawMaterialRepository).findById(2L);
        verify(productRawMaterialRepository)
                .existsByProductIdAndRawMaterialId(1L, 2L);

        verify(productRawMaterialRepository, never()).save(any());
        verifyNoMoreInteractions(
                productRepository,
                rawMaterialRepository,
                productRawMaterialRepository
        );
    }

    private static void setId(Object entity, Long id) {
        try {
            var field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}