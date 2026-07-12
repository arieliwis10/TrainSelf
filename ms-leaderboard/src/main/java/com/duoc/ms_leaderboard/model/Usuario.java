package com.duoc.ms_leaderboard.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    private Long id;

    private String nombre;

    @Column(name = "puntos_acumulados")
    private Integer puntosAcumulados;

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Integer getPuntosAcumulados() { return puntosAcumulados; }
}