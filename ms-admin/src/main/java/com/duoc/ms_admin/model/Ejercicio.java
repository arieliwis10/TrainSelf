package com.duoc.ms_admin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ejercicios")
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rutina_id", nullable = false)
    private Rutina rutina;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(name = "duracion_seg")
    private Integer duracionSeg;

    @Column(name = "descanso_seg")
    private Integer descansoSeg = 0;

    private Integer orden;

    @Column(name = "url_animacion")
    private String urlAnimacion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Rutina getRutina() { return rutina; }
    public void setRutina(Rutina rutina) { this.rutina = rutina; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getDuracionSeg() { return duracionSeg; }
    public void setDuracionSeg(Integer duracionSeg) { this.duracionSeg = duracionSeg; }
    public Integer getDescansoSeg() { return descansoSeg; }
    public void setDescansoSeg(Integer descansoSeg) { this.descansoSeg = descansoSeg; }
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
    public String getUrlAnimacion() { return urlAnimacion; }
    public void setUrlAnimacion(String urlAnimacion) { this.urlAnimacion = urlAnimacion; }
}