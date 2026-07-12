package com.duoc.ms_usuarios.controller;

import com.duoc.ms_usuarios.model.Sesion;
import com.duoc.ms_usuarios.model.Usuario;
import com.duoc.ms_usuarios.service.SesionRequest;
import com.duoc.ms_usuarios.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> perfil(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPerfil(id));
    }

    @GetMapping("/{id}/sesiones")
    public ResponseEntity<List<Sesion>> historial(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.historialSesiones(id));
    }

    @PostMapping("/{id}/sesiones")
    public ResponseEntity<Sesion> completarRutina(@PathVariable Long id, @RequestBody SesionRequest request) {
        return ResponseEntity.ok(usuarioService.completarRutina(id, request));
    }
}