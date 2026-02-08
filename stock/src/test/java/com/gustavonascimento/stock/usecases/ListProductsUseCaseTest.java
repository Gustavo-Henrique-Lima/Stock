package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.entities.Product;
import com.gustavonascimento.stock.records.product.GetProduct;
import com.gustavonascimento.stock.repositories.ProductRepository;
import com.gustavonascimento.stock.usecases.product.ListProductsUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListProductsUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ListProductsUseCase useCase;

    @Test
    void shouldReturnPagedProductsWhenDataExists() {
        Pageable pageable = PageRequest.of(0, 2);

        Product p1 = new Product(
                1L, "PR-001", "Product 1", BigDecimal.valueOf(10)
        );
        Product p2 = new Product(
                2L, "PR-002", "Product 2", BigDecimal.valueOf(20)
        );

        Page<Product> page =
                new PageImpl<>(List.of(p1, p2), pageable, 2);

        when(repository.findAll(pageable)).thenReturn(page);

        Page<GetProduct> result = useCase.execute(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent())
                .extracting(GetProduct::code)
                .containsExactly("PR-001", "PR-002");

        verify(repository).findAll(pageable);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnEmptyPageWhenNoProductsExist() {
        Pageable pageable = PageRequest.of(0, 5);

        Page<Product> emptyPage =
                Page.empty(pageable);

        when(repository.findAll(pageable)).thenReturn(emptyPage);

        Page<GetProduct> result = useCase.execute(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isZero();

        verify(repository).findAll(pageable);
        verifyNoMoreInteractions(repository);
    }
}