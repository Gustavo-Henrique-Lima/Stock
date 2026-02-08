package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.entities.ProductRawMaterial;
import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.productrawmaterial.GetProductRawMaterial;
import com.gustavonascimento.stock.repositories.ProductRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;
import com.gustavonascimento.stock.usecases.productrawmaterial.ListProductRawMaterialsUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListProductRawMaterialsUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ListProductRawMaterialsUseCase useCase;

    @Test
    void shouldListRawMaterialsWhenProductExists() {
        Product product = new Product(
                1L,
                "PR-01",
                "Product",
                BigDecimal.valueOf(100)
        );

        RawMaterial rm1 = new RawMaterial("RM-01", "Steel", 100.0);
        setId(rm1, 10L);

        RawMaterial rm2 = new RawMaterial("RM-02", "Copper", 50.0);
        setId(rm2, 20L);

        ProductRawMaterial prm1 =
                new ProductRawMaterial(null, product, rm1, 2.5);

        ProductRawMaterial prm2 =
                new ProductRawMaterial(null, product, rm2, 5.0);

        product.getRawMaterials().add(prm1);
        product.getRawMaterials().add(prm2);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        List<GetProductRawMaterial> result =
                useCase.execute(1L);

        assertThat(result).hasSize(2);

        assertThat(result)
                .extracting(GetProductRawMaterial::rawMaterialCode)
                .containsExactlyInAnyOrder("RM-01", "RM-02");

        verify(productRepository).findById(1L);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void shouldReturnEmptyListWhenProductHasNoRawMaterials() {
        Product product = new Product(
                1L,
                "PR-01",
                "Product",
                BigDecimal.valueOf(100)
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        List<GetProductRawMaterial> result =
                useCase.execute(1L);

        assertThat(result).isEmpty();

        verify(productRepository).findById(1L);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Produto não encontrado.");

        verify(productRepository).findById(999L);
        verifyNoMoreInteractions(productRepository);
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