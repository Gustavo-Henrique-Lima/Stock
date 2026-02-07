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
    void shouldReturnListOfRawMaterialsWhenDataExists() {

        RawMaterial rm1 = new RawMaterial("RM-001", "Steel", 100.0);
        RawMaterial rm2 = new RawMaterial("RM-002", "Aluminum", 50.0);

        setId(rm1, 1L);
        setId(rm2, 2L);

        when(repository.findAll()).thenReturn(List.of(rm1, rm2));

        List<GetRawMaterial> result = useCase.execute();

        assertThat(result)
                .isNotNull()
                .hasSize(2);

        assertThat(result)
                .extracting(GetRawMaterial::id, GetRawMaterial::code, GetRawMaterial::name, GetRawMaterial::stockQuantity)
                .containsExactly(
                        tuple(1L, "RM-001", "Steel", 100.0),
                        tuple(2L, "RM-002", "Aluminum", 50.0)
                );

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnEmptyListWhenNoRawMaterialsExist() {

        when(repository.findAll()).thenReturn(List.of());

        List<GetRawMaterial> result = useCase.execute();

        assertThat(result)
                .isNotNull()
                .isEmpty();

        verify(repository).findAll();
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