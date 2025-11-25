package org.example.mutantesmarchesi.service;
import org.example.mutantesmarchesi.entity.DnaRecord;
import org.example.mutantesmarchesi.exception.InvalidDnaException;
import org.example.mutantesmarchesi.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    // Mock del Detector de Mutantes, simula la lógica del algoritmo
    @Mock
    private MutantDetector mutantDetector;

    // Mock del Repositorio, simula la interacción con la Base de Datos (Persistencia)
    @Mock
    private DnaRecordRepository dnaRecordRepository;

    // Inyecta los Mocks en el Servicio que queremos probar
    @InjectMocks
    private MutantService mutantService;

    @Test
    @DisplayName("Debe analizar ADN mutante y guardarlo si no existe")
    void testAnalyzeMutantDnaAndSave() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        // Configuración de Mocks:
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty()); // No cacheado
        when(mutantDetector.isMutant(dna)).thenReturn(true); // Es mutante

        // Act
        boolean result = mutantService.analyzeDna(dna);

        // Assert:
        assertTrue(result);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class)); // Se debe guardar
    }

    @Test
    @DisplayName("Debe retornar resultado cacheado si ya existe en BD")
    void testReturnCachedResult() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DnaRecord existingRecord = new DnaRecord();
        existingRecord.setMutant(true);

        // Configuración de Mocks:
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(existingRecord)); // Está cacheado

        // Act
        boolean result = mutantService.analyzeDna(dna);

        // Assert:
        assertTrue(result);
        // Verificar que NO se llamó al detector ni a save (ahorro de recursos - Dedup)
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar InvalidDnaException si el ADN es nulo (Defensive Programming)")
    void testShouldThrowExceptionWhenDnaIsNull() {
        assertThrows(InvalidDnaException.class, () -> mutantService.analyzeDna(null));
    }
    @Test
    @DisplayName("Debe analizar ADN humano, guardarlo como 'false' y retornar false")
    void testAnalyzeHumanDnaAndSave() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CGCCTA", "TCACTG"};

        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(false);
        boolean result = mutantService.analyzeDna(dna);
        assertFalse(result);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
        verify(dnaRecordRepository).save(argThat(record -> !record.isMutant()));
        verify(dnaRecordRepository, times(1)).save(any());
    }
}