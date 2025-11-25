package org.example.mutantesmarchesi.service;
import org.springframework.stereotype.Service;

@Service
public class MutantDetector {

    // Constante para la secuencia (4 letras iguales)
    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0) return false;

        int n = dna.length;
        int sequenceCount = 0;

        // Optimización 1: Convertir a char[][] y Validar
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            // Validar que sea NxN (que la fila tenga el mismo largo que N)
            if (dna[i].length() != n) return false;

            matrix[i] = dna[i].toCharArray();

            // Validar caracteres inválidos (Solo A, T, C, G)
            for (char c : matrix[i]) {
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
                    return false; // Carácter inválido encontrado
                }
            }
        }

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {


                // Horizontal
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        // Optimización 4: Early Termination
                        if (sequenceCount > 1) return true;
                    }
                }

                // Vertical
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                // Diagonal Principal (\)
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkMainDiagonal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                // Diagonal Invertida (/)
                if (row <= n - SEQUENCE_LENGTH && col >= SEQUENCE_LENGTH - 1) {
                    if (checkSecondaryDiagonal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
            }
        }
        return false;
    }

    // Optimización 5: Direct Comparison (Sin bucles internos)
    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row][col+1] &&
                base == matrix[row][col+2] &&
                base == matrix[row][col+3];
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row+1][col] &&
                base == matrix[row+2][col] &&
                base == matrix[row+3][col];
    }

    private boolean checkMainDiagonal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row+1][col+1] &&
                base == matrix[row+2][col+2] &&
                base == matrix[row+3][col+3];
    }

    private boolean checkSecondaryDiagonal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row+1][col-1] &&
                base == matrix[row+2][col-2] &&
                base == matrix[row+3][col-3];
    }
}
