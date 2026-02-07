package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.GetRawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.UpdateRawMaterial;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;
import com.gustavonascimento.stock.usecases.exceptions.ResourceNotFoundException;
import com.gustavonascimento.stock.usecases.rawmaterial.UpdateRawMaterialUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRawMaterialUseCaseTest {

    @Mock
    private RawMaterialRepository repository;

    @InjectMocks
    private UpdateRawMaterialUseCase useCase;

    @Test
    void shouldUpdateRawMaterialWhenIdExists() {
        Long id = 1L;

        RawMaterial existing = new RawMaterial("RM-001", "Steel", 100.0);
        setId(existing, id);

        UpdateRawMaterial input = new UpdateRawMaterial(
                "Steel Updated",
                150.0
        );

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(RawMaterial.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        GetRawMaterial result = useCase.execute(id, input);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.code()).isEqualTo("RM-001");
        assertThat(result.name()).isEqualTo("Steel Updated");
        assertThat(result.stockQuantity()).isEqualTo(150.0);

        verify(repository).findById(id);
        verify(repository).save(existing);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldPersistUpdatedFieldsCorrectly() {
        Long id = 2L;

        RawMaterial existing = new RawMaterial("RM-002", "Aluminum", 50.0);
        setId(existing, id);

        UpdateRawMaterial input = new UpdateRawMaterial(
                "Aluminum Premium",
                75.0
        );

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(RawMaterial.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<RawMaterial> captor =
                ArgumentCaptor.forClass(RawMaterial.class);

        useCase.execute(id, input);

        verify(repository).save(captor.capture());

        RawMaterial updated = captor.getValue();

        assertThat(updated.getCode()).isEqualTo("RM-002");
        assertThat(updated.getName()).isEqualTo("Aluminum Premium");
        assertThat(updated.getStockQuantity()).isEqualTo(75.0);
    }

    @Test
    void shouldThrowExceptionWhenRawMaterialDoesNotExist() {
        Long id = 99L;
        UpdateRawMaterial input = new UpdateRawMaterial("Any", 10.0);

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(id, input))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Máteria prima não encontrada");

        verify(repository).findById(id);
        verify(repository, never()).save(any());
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