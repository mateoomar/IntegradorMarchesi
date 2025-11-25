package org.example.mutantesmarchesi.entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class DnaRecordTest {

    @Test
    @DisplayName("Debe probar los métodos generados por Lombok en DnaRecord")
    void testDnaRecordLombokMethods() {

        Long id = 1L;
        String hash = "hash123";
        boolean isMutant = true;
        LocalDateTime now = LocalDateTime.now();

        DnaRecord record1 = DnaRecord.builder()
                .id(id)
                .dnaHash(hash)
                .isMutant(isMutant)
                .createdAt(now)
                .build();

        assertEquals(id, record1.getId());
        assertEquals(hash, record1.getDnaHash());
        assertTrue(record1.isMutant());
        assertEquals(now, record1.getCreatedAt());

        DnaRecord record2 = new DnaRecord();
        record2.setId(id);
        record2.setDnaHash(hash);
        record2.setMutant(isMutant);
        record2.setCreatedAt(now);

        DnaRecord record3 = new DnaRecord(id, hash, isMutant, now);
        assertNotNull(record1.toString());
    }
}
