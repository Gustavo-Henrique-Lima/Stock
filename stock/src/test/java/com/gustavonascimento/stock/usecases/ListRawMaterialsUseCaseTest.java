package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.GetRawMaterial;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;
import com.gustavonascimento.stock.usecases.rawmaterial.ListRawMaterialsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListRawMaterialsUseCaseTest {

    @Mock
    private RawMaterialRepository repository;

    @InjectMocks
    private ListRawMaterialsUseCase useCase;

    @Test
    void shouldReturnPageOfRawMaterialsWhenDataExists() {

        RawMaterial rm1 = new RawMaterial("FG", "Fogo", 100.0);
        RawMaterial rm2 = new RawMaterial("AR", "Ar", 50.0);

        setId(rm1, 1L);
        setId(rm2, 2L);

        Pageable pageable = PageRequest.of(0, 10);

        Page<RawMaterial> page = new PageImpl<>(
                List.of(rm1, rm2),
                pageable,
                2
        );

        when(repository.findAll(pageable)).thenReturn(page);

        Page<GetRawMaterial> result = useCase.execute(pageable);

        assertThat(result)
                .isNotNull();

        assertThat(result.getContent())
                .hasSize(2)
                .extracting(
                        GetRawMaterial::id,
                        GetRawMaterial::code,
                        GetRawMaterial::name,
                        GetRawMaterial::stockQuantity
                )
                .containsExactly(
                        tuple(1L, "FG", "Fogo", 100.0),
                        tuple(2L, "AR", "Ar", 50.0)
                );

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getNumber()).isEqualTo(0);

        verify(repository).findAll(pageable);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnEmptyPageWhenNoRawMaterialsExist() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<RawMaterial> emptyPage = Page.empty(pageable);

        when(repository.findAll(pageable)).thenReturn(emptyPage);

        Page<GetRawMaterial> result = useCase.execute(pageable);

        assertThat(result)
                .isNotNull()
                .isEmpty();

        assertThat(result.getTotalElements()).isZero();
        assertThat(result.getTotalPages()).isZero();
        assertThat(result.getNumber()).isEqualTo(0);

        verify(repository).findAll(pageable);
        verifyNoMoreInteractions(repository);
    }

    private static void setId(RawMaterial entity, Long id) {
        try {
            var field = RawMaterial.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}