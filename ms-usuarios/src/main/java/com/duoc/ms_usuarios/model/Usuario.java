package com.duoc.ms_usuarios.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;

    @Column(name = "password_hash")
    private String passwordHash;

    private String rol;

    @Column(name = "puntos_acumulados")
    private Integer puntosAcumulados;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getRol() { return rol; }
    public Integer getPuntosAcumulados() { return puntosAcumulados; }
    public void setPuntosAcumulados(Integer puntosAcumulados) { this.puntosAcumulados = puntosAcumulados; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
}