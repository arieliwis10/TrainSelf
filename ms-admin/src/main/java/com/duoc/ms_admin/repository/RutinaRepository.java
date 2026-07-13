package com.duoc.ms_admin.repository;

import com.duoc.ms_admin.model.Rutina;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RutinaRepository extends JpaRepository<Rutina, Long> {

    @Query("SELECT r.objetivo.nombre, COUNT(r) FROM Rutina r GROUP BY r.objetivo.nombre")
    List<Object[]> contarPorObjetivo();


}