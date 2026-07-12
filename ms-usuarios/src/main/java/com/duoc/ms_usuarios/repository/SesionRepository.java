package com.duoc.ms_usuarios.repository;

import com.duoc.ms_usuarios.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByUsuarioIdOrderByFechaInicioDesc(Long usuarioId);
}