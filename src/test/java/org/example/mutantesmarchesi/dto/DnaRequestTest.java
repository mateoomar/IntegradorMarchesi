package org.example.mutantesmarchesi.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DnaRequestTest {

    @Test
    @DisplayName("Debe probar los métodos generados por Lombok en DnaRequest")
    void testDnaRequestLombokMethods() {

        DnaRequest request1 = new DnaRequest();
        String[] dna = {"AAAA", "CCCC", "TTTT", "GGGG"};
        request1.setDna(dna);

        assertArrayEquals(dna, request1.getDna());

        DnaRequest request2 = new DnaRequest(dna);
        assertArrayEquals(dna, request2.getDna());

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1, new DnaRequest(new String[]{"OTRO"}));

        String stringRepresentation = request1.toString();
        assertTrue(stringRepresentation.contains("DnaRequest"));
        assertTrue(stringRepresentation.contains("dna="));
    }
}
