package com.duoc.ms_admin.repository;

import com.duoc.ms_admin.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SesionRepository extends JpaRepository<Sesion, Long> {

    @Query("""
        SELECT r.id, r.nombre, COUNT(s.id)
        FROM Sesion s JOIN s.rutina r
        GROUP BY r.id, r.nombre
        ORDER BY COUNT(s.id) DESC
    """)
    List<Object[]> rutinasMasUsadas();
}