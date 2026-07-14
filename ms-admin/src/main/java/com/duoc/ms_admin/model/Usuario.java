package com.duoc.ms_admin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    private Long id;

    private String nombre;
    private String correo;
    private String rol;

    @Column(name = "puntos_acumulados")
    private Integer puntosAcumulados;

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getRol() { return rol; }
    public Integer getPuntosAcumulados() { return puntosAcumulados; }
}