package com.duoc.ms_admin.repository;

import com.duoc.ms_admin.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SesionRepository extends JpaRepository<Sesion, Long> {

    @Query("""
        SELECT r.id, r.nombre, COUNT(s.id)
        FROM Sesion s JOIN s.rutina r
        GROUP BY r.id, r.nombre
        ORDER BY COUNT(s.id) DESC
    """)
    List<Object[]> rutinasMasUsadas();

    @Query("SELECT COUNT(s) FROM Sesion s WHERE s.fechaInicio >= :fecha")
        long contarSesionesDesde(@Param("fecha") LocalDateTime fecha);

    @Query("SELECT COUNT(DISTINCT s.usuarioId) FROM Sesion s WHERE s.fechaInicio >= :fecha")
        long contarUsuariosActivosDesde(@Param("fecha") LocalDateTime fecha);

    @Query("""
        SELECT CAST(s.fechaInicio AS date), COUNT(s)
        FROM Sesion s
        WHERE s.fechaInicio >= :desde
        GROUP BY CAST(s.fechaInicio AS date)
        ORDER BY CAST(s.fechaInicio AS date)
    """)
    List<Object[]> actividadPorDia(@Param("desde") LocalDateTime desde);

    @Query("""
        SELECT s.usuarioId, COUNT(s)
        FROM Sesion s
        WHERE s.completada = true
        GROUP BY s.usuarioId
    """)
    List<Object[]> sesionesCompletadasPorUsuario();       

}