package com.duoc.ms_usuarios;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "servicio", "ms-usuarios",
            "Oriana", "activo"
        );
    }

    @GetMapping
    public List<Map<String, Object>> listarUsuarios() {
        return List.of(
            Map.of("id", 1, "nombre", "Ariel", "email", "ariel@duoc.cl"),
            Map.of("id", 2, "nombre", "Carlos", "email", "carlos@duoc.cl")
        );
    }

    @GetMapping("/{id}")
    public Map<String, Object> obtenerUsuario(@PathVariable int id) {
        return Map.of("id", id, "nombre", "Usuario " + id, "email", "usuario" + id + "@duoc.cl");
    }
}