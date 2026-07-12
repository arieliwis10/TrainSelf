package com.duoc.ms_auth.controller;

import com.duoc.ms_auth.dto.AuthResponse;
import com.duoc.ms_auth.dto.LoginRequest;
import com.duoc.ms_auth.dto.RegisterRequest;
import com.duoc.ms_auth.model.Usuario;
import com.duoc.ms_auth.repository.UsuarioRepository;
import com.duoc.ms_auth.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El correo ya está registrado"));
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setRol("USER");

        usuarioRepository.save(usuario);

        String token = jwtService.generarToken(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new AuthResponse(token, usuario.getId(), usuario.getNombre(),
                        usuario.getCorreo(), usuario.getRol())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElse(null);

        if (usuario == null || !passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Correo o contraseña incorrectos"));
        }

        String token = jwtService.generarToken(usuario);
        return ResponseEntity.ok(
                new AuthResponse(token, usuario.getId(), usuario.getNombre(),
                        usuario.getCorreo(), usuario.getRol())
        );
    }
}