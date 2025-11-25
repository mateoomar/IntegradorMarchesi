package org.example.mutantesmarchesi.service;

import lombok.RequiredArgsConstructor;
import org.example.mutantesmarchesi.dto.StatsResponse;
import org.example.mutantesmarchesi.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    public StatsResponse getStats() {
        long countMutant = dnaRecordRepository.countByIsMutant(true);
        long countHuman = dnaRecordRepository.countByIsMutant(false);

        double ratio = 0.0;
        if (countHuman > 0) {
            ratio = (double) countMutant / countHuman;
        }

        return new StatsResponse(countMutant, countHuman, ratio);
    }
}