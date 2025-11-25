package org.example.mutantesmarchesi.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import org.example.mutantesmarchesi.validation.ValidDna;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DnaRequest {
    @NotNull
    @ValidDna // Validación personalizada
    @Schema(example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]")
    @EqualsAndHashCode.Include
    private String[] dna;
}
