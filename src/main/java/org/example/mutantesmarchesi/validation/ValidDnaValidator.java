package org.example.mutantesmarchesi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidDnaValidator implements ConstraintValidator<ValidDna, String[]> {

    private static final Pattern VALID_CHARACTERS = Pattern.compile("^[ATCG]+$");

    @Override
    public boolean isValid(String[] adn, ConstraintValidatorContext context) {
        if (adn == null) return false;
        int n = adn.length;
        if (n == 0) return false;

        for (String row : adn) {
            if (row == null || row.length() != n) return false;
            if (!VALID_CHARACTERS.matcher(row).matches()) return false;
        }
        return true;
    }
}
