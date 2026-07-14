package com.duoc.ms_auth.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    private final SecurityConfig securityConfig = new SecurityConfig();

    @Test
    void passwordEncoder_esInstanciaDeBCrypt() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();

        assertInstanceOf(BCryptPasswordEncoder.class, encoder);
    }

    @Test
    void passwordEncoder_codificaYVerificaCorrectamente() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();

        String hash = encoder.encode("12345678");

        assertNotEquals("12345678", hash);
        assertTrue(encoder.matches("12345678", hash));
        assertFalse(encoder.matches("otraClave", hash));
    }

    @Test
    void passwordEncoder_generaHashesDistintosParaLaMismaClave() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();

        String hash1 = encoder.encode("12345678");
        String hash2 = encoder.encode("12345678");

        // BCrypt usa un salt aleatorio, por lo que dos hashes de la misma
        // clave nunca deben ser idénticos, aunque ambos la validen igual.
        assertNotEquals(hash1, hash2);
        assertTrue(encoder.matches("12345678", hash1));
        assertTrue(encoder.matches("12345678", hash2));
    }
}