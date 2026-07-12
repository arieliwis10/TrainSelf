package com.duoc.ms_usuarios.model;

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

    @Column(name = "rutina_id", nullable = false)
    private Long rutinaId;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio = LocalDateTime.now();

    private Boolean completada = false;

    @Column(name = "puntos_obtenidos")
    private Integer puntosObtenidos = 0;

    @Column(name = "duracion_real_min")
    private Integer duracionRealMin;

    public Long getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getRutinaId() { return rutinaId; }
    public void setRutinaId(Long rutinaId) { this.rutinaId = rutinaId; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public Boolean getCompletada() { return completada; }
    public void setCompletada(Boolean completada) { this.completada = completada; }
    public Integer getPuntosObtenidos() { return puntosObtenidos; }
    public void setPuntosObtenidos(Integer puntosObtenidos) { this.puntosObtenidos = puntosObtenidos; }
    public Integer getDuracionRealMin() { return duracionRealMin; }
    public void setDuracionRealMin(Integer duracionRealMin) { this.duracionRealMin = duracionRealMin; }
}