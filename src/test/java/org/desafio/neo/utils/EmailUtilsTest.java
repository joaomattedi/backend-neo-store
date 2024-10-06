package org.desafio.neo.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailUtilsTest {
    @Test
    void testValidEmail() {
        String validEmail = "test.email@example.com";
        assertTrue(EmailUtils.isValidEmail(validEmail));
    }

    @Test
    void testInvalidEmailNoAtSymbol() {
        String invalidEmail = "test.email.example.com";
        assertFalse(EmailUtils.isValidEmail(invalidEmail));
    }

    @Test
    void testInvalidEmailNoDomain() {
        String invalidEmail = "test.email@";
        assertFalse(EmailUtils.isValidEmail(invalidEmail));
    }

    @Test
    void testInvalidEmailWithoutDotCom() {
        String invalidEmail = "test.email@example";
        assertFalse(EmailUtils.isValidEmail(invalidEmail));
    }

    @Test
    void testEmailWithSpecialCharacters() {
        String invalidEmail = "user+mailbox/department=shipping@example.com";
        assertFalse(EmailUtils.isValidEmail(invalidEmail));
    }

    @Test
    void testEmailWithSpecialHyphenAndUnderline() {
        String validEmail = "test_or-email@example.com";
        assertTrue(EmailUtils.isValidEmail(validEmail));
    }

    @Test
    void testNullEmail() {
        assertFalse(EmailUtils.isValidEmail(null));
    }
}
