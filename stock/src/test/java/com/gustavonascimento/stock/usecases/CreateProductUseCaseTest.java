package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.records.product.CreateProduct;
import com.gustavonascimento.stock.records.product.GetProduct;
import com.gustavonascimento.stock.repositories.ProductRepository;
import com.gustavonascimento.stock.usecases.product.CreateProductUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private CreateProductUseCase useCase;

    @Test
    void shouldCreateProductSuccessfully() {
        CreateProduct input = new CreateProduct(
                "PR-015",
                "Produto Testado",
                BigDecimal.valueOf(199.90)
        );

        Product savedEntity = new Product(
                1L,
                "PR-015",
                "Produto Testado",
                BigDecimal.valueOf(199.90)
        );

        when(repository.save(any(Product.class)))
                .thenReturn(savedEntity);

        GetProduct result = useCase.execute(input);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.code()).isEqualTo("PR-015");
        assertThat(result.name()).isEqualTo("Produto Testado");
        assertThat(result.price()).isEqualTo(BigDecimal.valueOf(199.90));

        verify(repository).save(any(Product.class));
    }

    @Test
    void shouldMapCreateProductRecordToEntityCorrectly() {
        CreateProduct input = new CreateProduct(
                "PR-007",
                "Outro Produto",
                BigDecimal.valueOf(50)
        );

        when(repository.save(any(Product.class)))
                .thenAnswer(invocation -> {
                    Product entity = invocation.getArgument(0);
                    entity.setCode(entity.getCode());
                    return entity;
                });

        useCase.execute(input);

        ArgumentCaptor<Product> captor =
                ArgumentCaptor.forClass(Product.class);

        verify(repository).save(captor.capture());

        Product captured = captor.getValue();

        assertThat(captured.getCode()).isEqualTo("PR-007");
        assertThat(captured.getName()).isEqualTo("Outro Produto");
        assertThat(captured.getPrice()).isEqualTo(BigDecimal.valueOf(50));
    }
}