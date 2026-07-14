package com.duoc.ms_auth.controller;

import com.duoc.ms_auth.dto.LoginRequest;
import com.duoc.ms_auth.dto.RegisterRequest;
import com.duoc.ms_auth.model.Usuario;
import com.duoc.ms_auth.repository.UsuarioRepository;
import com.duoc.ms_auth.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;

    @Test
    void register_correoNuevo_creaUsuarioYRetorna201() {
        RegisterRequest request = new RegisterRequest();
        request.setNombre("Ariel Test2");
        request.setCorreo("ariel2@test.com");
        request.setPassword("12345678");

        when(usuarioRepository.existsByCorreo("ariel2@test.com")).thenReturn(false);
        when(passwordEncoder.encode("12345678")).thenReturn("$2a$10$hashSimulado");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(inv -> {
            Usuario u = inv.getArgument(0);
            u.setId(4L);
            return u;
        });
        when(jwtService.generarToken(any(Usuario.class))).thenReturn("token-simulado");

        ResponseEntity<?> respuesta = authController.register(request);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void register_correoYaRegistrado_retorna409YNoGuarda() {
        RegisterRequest request = new RegisterRequest();
        request.setCorreo("ariel@test.com");
        request.setPassword("12345678");

        when(usuarioRepository.existsByCorreo("ariel@test.com")).thenReturn(true);

        ResponseEntity<?> respuesta = authController.register(request);

        assertEquals(HttpStatus.CONFLICT, respuesta.getStatusCode());
        verify(usuarioRepository, never()).save(any());
        verify(jwtService, never()).generarToken(any());
    }

    @Test
    void login_credencialesCorrectas_retorna200ConToken() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCorreo("ariel@test.com");
        usuario.setPasswordHash("$2a$10$hashReal");
        usuario.setRol("ADMIN");

        LoginRequest request = new LoginRequest();
        request.setCorreo("ariel@test.com");
        request.setPassword("12345678");

        when(usuarioRepository.findByCorreo("ariel@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("12345678", "$2a$10$hashReal")).thenReturn(true);
        when(jwtService.generarToken(usuario)).thenReturn("token-valido");

        ResponseEntity<?> respuesta = authController.login(request);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    void login_passwordIncorrecta_retorna401() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("ariel@test.com");
        usuario.setPasswordHash("$2a$10$hashReal");

        LoginRequest request = new LoginRequest();
        request.setCorreo("ariel@test.com");
        request.setPassword("contraseñaIncorrecta");

        when(usuarioRepository.findByCorreo("ariel@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("contraseñaIncorrecta", "$2a$10$hashReal")).thenReturn(false);

        ResponseEntity<?> respuesta = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, respuesta.getStatusCode());
        verify(jwtService, never()).generarToken(any());
    }

    @Test
    void login_correoInexistente_retorna401SinConsultarPassword() {
        LoginRequest request = new LoginRequest();
        request.setCorreo("noexiste@test.com");
        request.setPassword("12345678");

        when(usuarioRepository.findByCorreo("noexiste@test.com")).thenReturn(Optional.empty());

        ResponseEntity<?> respuesta = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, respuesta.getStatusCode());
        verify(passwordEncoder, never()).matches(any(), any());
    }
}