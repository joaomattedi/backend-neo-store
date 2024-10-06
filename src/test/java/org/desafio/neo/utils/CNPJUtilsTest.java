package org.desafio.neo.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CNPJUtilsTest {
    @Test
    void testValidCNPJ() {
        String validCNPJ = "12.345.678/0001-95";
        assertTrue(CNPJUtils.isValidCNPJ(validCNPJ));
    }

    @Test
    void testInvalidCNPJLength() {
        String invalidCNPJ = "12345";
        assertFalse(CNPJUtils.isValidCNPJ(invalidCNPJ));
    }

    @Test
    void testInvalidCNPJCharacters() {
        String invalidCNPJ = "12.345.678/000X-95";
        assertFalse(CNPJUtils.isValidCNPJ(invalidCNPJ));
    }

    @Test
    void testInvalidCNPJCheckDigits() {
        String invalidCNPJ = "12.345.678/0001-00";
        assertFalse(CNPJUtils.isValidCNPJ(invalidCNPJ));
    }

    @Test
    void testValidCNPJWithoutFormat() {
        String validCNPJ = "86206846000106";
        assertTrue(CNPJUtils.isValidCNPJ(validCNPJ));
    }
}
