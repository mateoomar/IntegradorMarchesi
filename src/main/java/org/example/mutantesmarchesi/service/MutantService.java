package org.example.mutantesmarchesi.service;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.example.mutantesmarchesi.entity.DnaRecord;
import org.example.mutantesmarchesi.exception.InvalidDnaException;
import org.example.mutantesmarchesi.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository repository;

    public boolean analyzeDna(String[] adn) {

        if (adn == null || adn.length == 0) {
            throw new InvalidDnaException("El ADN no puede ser nulo ni vacío");
        }

        String hash = calculateHash(adn);

        Optional<DnaRecord> existingRecord = repository.findByDnaHash(hash);
        if (existingRecord.isPresent()) {
            return existingRecord.get().isMutant();
        }

        boolean isMutant = mutantDetector.isMutant(adn);

        DnaRecord record = DnaRecord.builder()
                .dnaHash(hash)
                .isMutant(isMutant)
                .createdAt(LocalDateTime.now())
                .build();
        repository.save(record);

        return isMutant;
    }
    private String calculateHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String joined = String.join("", dna);
            byte[] encoded = digest.digest(joined.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : encoded) {
                String h = Integer.toHexString(0xff & b);
                if (h.length() == 1) hex.append('0');
                hex.append(h);
            }
            return hex.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No se pudo calcular el hash SHA-256", e);
        }
    }

    private String computeHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(String.join("", dna).getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error generating DNA hash", e);
        }
    }

}