package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.GetRawMaterial;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;
import com.gustavonascimento.stock.usecases.rawmaterial.GetRawMaterialUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetRawMaterialUseCaseTest {

    @Mock
    private RawMaterialRepository repository;

    @InjectMocks
    private GetRawMaterialUseCase useCase;

    @Test
    void shouldReturnRawMaterialWhenIdExists() {
        Long id = 1L;

        RawMaterial entity = new RawMaterial("RM-001", "Steel", 100.0);
        setId(entity, id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        GetRawMaterial result = useCase.execute(id);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.code()).isEqualTo("RM-001");
        assertThat(result.name()).isEqualTo("Steel");
        assertThat(result.stockQuantity()).isEqualTo(100.0);

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenRawMaterialDoesNotExist() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Matéria-prima não encontrada");

        verify(repository).findById(id);
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