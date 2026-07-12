package com.duoc.ms_admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sesiones")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @ManyToOne
    @JoinColumn(name = "rutina_id", nullable = false)
    private Rutina rutina;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    private Boolean completada;

    @Column(name = "puntos_obtenidos")
    private Integer puntosObtenidos;

    public Long getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public Rutina getRutina() { return rutina; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public Boolean getCompletada() { return completada; }
    public Integer getPuntosObtenidos() { return puntosObtenidos; }
}