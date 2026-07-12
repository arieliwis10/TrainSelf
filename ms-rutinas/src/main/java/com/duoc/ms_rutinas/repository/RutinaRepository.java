package com.duoc.ms_rutinas.repository;

import com.duoc.ms_rutinas.model.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RutinaRepository extends JpaRepository<Rutina, Long> {
    List<Rutina> findByObjetivo_NombreAndNivel(String objetivoNombre, String nivel);
    List<Rutina> findByObjetivo_Nombre(String objetivoNombre);
}