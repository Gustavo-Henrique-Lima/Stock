package com.gustavonascimento.stock.usecases;

import com.gustavonascimento.stock.records.rawmaterial.AssociateRawMaterial;
import com.gustavonascimento.stock.repositories.RawMaterialRepository;
import com.gustavonascimento.stock.usecases.rawmaterial.AssociateRawMaterialDataUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssociateRawMaterialDataUseCaseTest {

    @Mock
    private RawMaterialRepository repository;

    @InjectMocks
    private AssociateRawMaterialDataUseCase useCase;

    @Test
    void shouldReturnRawMaterialSummaries() {
        List<AssociateRawMaterial> summaries = List.of(
                new AssociateRawMaterial(1L, "Steel"),
                new AssociateRawMaterial(2L, "Copper")
        );

        when(repository.findAllSummaries())
                .thenReturn(summaries);

        List<AssociateRawMaterial> result = useCase.execute();

        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .extracting(AssociateRawMaterial::name)
                .containsExactly("Steel", "Copper");

        verify(repository).findAllSummaries();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnEmptyListWhenNoRawMaterialsExist() {
        when(repository.findAllSummaries())
                .thenReturn(List.of());

        List<AssociateRawMaterial> result = useCase.execute();

        assertThat(result)
                .isNotNull()
                .isEmpty();

        verify(repository).findAllSummaries();
        verifyNoMoreInteractions(repository);
    }
}
