package org.example.mutantesmarchesi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mutantesmarchesi.dto.DnaRequest;
import org.example.mutantesmarchesi.dto.StatsResponse;
import org.example.mutantesmarchesi.service.MutantService;
import org.example.mutantesmarchesi.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Mutant Detector", description = "API para detectar mutantes")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService; // (Implementar lógica similar a MutantService)

    @Operation(summary = "Verifica si una secuencia de ADN pertenece a un mutante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es Mutante", content = @Content),
            @ApiResponse(responseCode = "403", description = "No es Mutante", content = @Content),
            // ESTA ES LA ANOTACIÓN PARA EL CÓDIGO 400
            @ApiResponse(responseCode = "400", description = "Solicitud Inválida: El JSON está mal formado o no cumple con las reglas de validación (ej. campo 'dna' nulo).", content = @Content)
    })
    @PostMapping("/mutant")

    public ResponseEntity<Void> detectMutant(@Valid @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.analyzeDna(request.getDna());
        if (isMutant) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/stats")
    @Operation(summary = "Obtener estadísticas de verificaciones")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}