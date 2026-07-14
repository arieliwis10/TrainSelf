package com.duoc.ms_usuarios.service;

import com.duoc.ms_usuarios.model.Sesion;
import com.duoc.ms_usuarios.model.Usuario;
import com.duoc.ms_usuarios.repository.SesionRepository;
import com.duoc.ms_usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SesionRepository sesionRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioEjemplo;

    private void setField(Object target, String name, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        f.set(target, value);
    }

    @BeforeEach
    void setUp() throws Exception {
        usuarioEjemplo = new Usuario();
        setField(usuarioEjemplo, "id", 4L);
        usuarioEjemplo.setPuntosAcumulados(0);
    }

    @Test
    void obtenerPerfil_usuarioExistente_retornaUsuario() {
        when(usuarioRepository.findById(4L)).thenReturn(Optional.of(usuarioEjemplo));

        Usuario resultado = usuarioService.obtenerPerfil(4L);

        assertEquals(4L, resultado.getId());
    }

    @Test
    void obtenerPerfil_usuarioInexistente_lanzaExcepcion() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.obtenerPerfil(999L));
    }

    @Test
    void historialSesiones_retornaListaOrdenadaPorFecha() {
        Sesion s1 = new Sesion();
        s1.setUsuarioId(4L);
        when(sesionRepository.findByUsuarioIdOrderByFechaInicioDesc(4L))
                .thenReturn(List.of(s1));

        List<Sesion> resultado = usuarioService.historialSesiones(4L);

        assertEquals(1, resultado.size());
        verify(sesionRepository).findByUsuarioIdOrderByFechaInicioDesc(4L);
    }

    @Test
    void completarRutina_sumaCincuentaPuntosAlUsuario() {
        when(usuarioRepository.findById(4L)).thenReturn(Optional.of(usuarioEjemplo));
        when(sesionRepository.save(any(Sesion.class))).thenAnswer(inv -> inv.getArgument(0));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        SesionRequest request = new SesionRequest();
        request.setRutinaId(1L);
        request.setDuracionRealMin(12);

        Sesion resultado = usuarioService.completarRutina(4L, request);

        assertEquals(50, resultado.getPuntosObtenidos());
        assertTrue(resultado.getCompletada());
        assertEquals(1L, resultado.getRutinaId());

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        assertEquals(50, captor.getValue().getPuntosAcumulados());
    }

    @Test
    void completarRutina_usuarioConPuntosPrevios_acumulaCorrectamente() {
        usuarioEjemplo.setPuntosAcumulados(100);
        when(usuarioRepository.findById(4L)).thenReturn(Optional.of(usuarioEjemplo));
        when(sesionRepository.save(any(Sesion.class))).thenAnswer(inv -> inv.getArgument(0));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        SesionRequest request = new SesionRequest();
        request.setRutinaId(2L);
        request.setDuracionRealMin(20);

        usuarioService.completarRutina(4L, request);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        assertEquals(150, captor.getValue().getPuntosAcumulados());
    }

    @Test
    void completarRutina_usuarioInexistente_lanzaExcepcionYNoGuardaSesion() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        SesionRequest request = new SesionRequest();
        request.setRutinaId(1L);
        request.setDuracionRealMin(10);

        assertThrows(RuntimeException.class, () -> usuarioService.completarRutina(999L, request));
        verify(sesionRepository, never()).save(any());
    }
}