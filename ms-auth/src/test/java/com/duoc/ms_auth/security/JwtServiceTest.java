package com.duoc.ms_auth.security;

import com.duoc.ms_auth.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    // Secreto de prueba (Base64, 256 bits) — nunca el mismo que el de producción
    private final String secretDePrueba = "LqA2MikO+B7UXbSeK6QAX+iQZHBIA///yGmd4VKlt/o=";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", secretDePrueba);
        ReflectionTestUtils.setField(jwtService, "expirationMs", 3600000L);
    }

    private Usuario crearUsuario() {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setNombre("Ariel Galvez");
        u.setCorreo("ariel@test.com");
        u.setRol("ADMIN");
        return u;
    }

    @Test
    void generarToken_incluyeElCorreoComoSubject() {
        String token = jwtService.generarToken(crearUsuario());

        Claims claims = parsearClaims(token);

        assertEquals("ariel@test.com", claims.getSubject());
    }

    @Test
    void generarToken_incluyeIdNombreYRolComoClaims() {
        String token = jwtService.generarToken(crearUsuario());

        Claims claims = parsearClaims(token);

        assertEquals(1, ((Number) claims.get("id")).intValue());
        assertEquals("Ariel Galvez", claims.get("nombre"));
        assertEquals("ADMIN", claims.get("rol"));
    }

    @Test
    void generarToken_generaTresPartesSeparadasPorPunto() {
        String token = jwtService.generarToken(crearUsuario());

        String[] partes = token.split("\\.");

        assertEquals(3, partes.length);
    }

    @Test
    void generarToken_expiracionEsPosteriorAEmision() {
        String token = jwtService.generarToken(crearUsuario());

        Claims claims = parsearClaims(token);

        assertTrue(claims.getExpiration().after(claims.getIssuedAt()));
    }

    @Test
    void generarToken_conSecretoDistinto_fallaLaValidacion() {
        String token = jwtService.generarToken(crearUsuario());
        // Secreto de 256 bits válido, pero distinto al usado para firmar
        SecretKey otraLlave = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode("YW5vdGhlclNlY3JldEtleUZvclRlc3RpbmdQdXJwb3Nlcw=="));

        assertThrows(Exception.class, () ->
                Jwts.parser().verifyWith(otraLlave).build().parseSignedClaims(token));
    }

    private Claims parsearClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretDePrueba));
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}