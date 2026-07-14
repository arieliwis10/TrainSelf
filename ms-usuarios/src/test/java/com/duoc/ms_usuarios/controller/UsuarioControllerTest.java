package com.duoc.ms_usuarios.controller;

import com.duoc.ms_usuarios.model.Sesion;
import com.duoc.ms_usuarios.model.Usuario;
import com.duoc.ms_usuarios.service.SesionRequest;
import com.duoc.ms_usuarios.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    void perfil_delegaAlServicioYRetorna200() {
        Usuario usuario = new Usuario();
        when(usuarioService.obtenerPerfil(4L)).thenReturn(usuario);

        ResponseEntity<Usuario> respuesta = usuarioController.perfil(4L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(usuario, respuesta.getBody());
        verify(usuarioService).obtenerPerfil(4L);
    }

    @Test
    void historial_delegaAlServicioYRetorna200() {
        List<Sesion> sesiones = List.of(new Sesion());
        when(usuarioService.historialSesiones(4L)).thenReturn(sesiones);

        ResponseEntity<List<Sesion>> respuesta = usuarioController.historial(4L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void historial_sinSesiones_retornaListaVacia() {
        when(usuarioService.historialSesiones(4L)).thenReturn(List.of());

        ResponseEntity<List<Sesion>> respuesta = usuarioController.historial(4L);

        assertTrue(respuesta.getBody().isEmpty());
    }

    @Test
    void completarRutina_delegaAlServicioConLosParametrosCorrectos() {
        SesionRequest request = new SesionRequest();
        request.setRutinaId(1L);
        request.setDuracionRealMin(12);

        Sesion sesionCreada = new Sesion();
        sesionCreada.setPuntosObtenidos(50);

        when(usuarioService.completarRutina(4L, request)).thenReturn(sesionCreada);

        ResponseEntity<Sesion> respuesta = usuarioController.completarRutina(4L, request);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(50, respuesta.getBody().getPuntosObtenidos());
        verify(usuarioService).completarRutina(4L, request);
    }
}