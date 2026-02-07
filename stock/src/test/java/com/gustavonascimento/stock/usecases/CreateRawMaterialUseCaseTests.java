package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.entities.RawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.CreateRawMaterial;
import com.gustavonascimento.stock.records.rawmaterial.GetRawMaterial;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;
import com.gustavonascimento.stock.usecases.rawmaterial.CreateRawMaterialUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRawMaterialUseCaseTest {

    @Mock
    private RawMaterialRepository repository;

    @InjectMocks
    private CreateRawMaterialUseCase useCase;

    @Test
    void shouldCreateAndReturnRawMaterialSuccessfully() {
        CreateRawMaterial input = new CreateRawMaterial(
                "RM-001",
                "Steel",
                100.0
        );

        RawMaterial savedEntity = new RawMaterial(
                "RM-001",
                "Steel",
                100.0
        );

        savedEntity.setStockQuantity(100.0);

        setId(savedEntity, 1L);

        when(repository.save(any(RawMaterial.class)))
                .thenReturn(savedEntity);

        GetRawMaterial result = useCase.execute(input);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.code()).isEqualTo("RM-001");
        assertThat(result.name()).isEqualTo("Steel");
        assertThat(result.stockQuantity()).isEqualTo(100.0);

        verify(repository).save(any(RawMaterial.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldPersistEntityWithCorrectMappedFields() {
        CreateRawMaterial input = new CreateRawMaterial(
                "RM-002",
                "Aluminum",
                50.0
        );

        when(repository.save(any(RawMaterial.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<RawMaterial> captor =
                ArgumentCaptor.forClass(RawMaterial.class);

        useCase.execute(input);

        verify(repository).save(captor.capture());

        RawMaterial entitySaved = captor.getValue();

        assertThat(entitySaved.getCode()).isEqualTo("RM-002");
        assertThat(entitySaved.getName()).isEqualTo("Aluminum");
        assertThat(entitySaved.getStockQuantity()).isEqualTo(50.0);
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

