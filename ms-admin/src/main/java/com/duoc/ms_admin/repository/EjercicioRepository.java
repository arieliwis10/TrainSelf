package com.duoc.ms_admin.repository;

import com.duoc.ms_admin.model.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
    List<Ejercicio> findByRutina_IdOrderByOrdenAsc(Long rutinaId);
}