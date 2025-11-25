package org.example.mutantesmarchesi.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StatsResponseTest {

    @Test
    @DisplayName("Debe probar los métodos generados por Lombok en StatsResponse")
    void testStatsResponseLombokMethods() {

        StatsResponse stats1 = new StatsResponse(40L, 100L, 0.4);

        assertEquals(40L, stats1.getCountMutantDna());
        assertEquals(100L, stats1.getCountHumanDna());
        assertEquals(0.4, stats1.getRatio());

        StatsResponse stats2 = new StatsResponse();
        stats2.setCountMutantDna(40L);
        stats2.setCountHumanDna(100L);
        stats2.setRatio(0.4);

        assertEquals(stats1, stats2);
        assertEquals(stats1.hashCode(), stats2.hashCode());

        assertNotNull(stats1.toString());
    }
}
