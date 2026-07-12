package com.duoc.ms_usuarios.service;

public class SesionRequest {
    private Long rutinaId;
    private Integer duracionRealMin;

    public Long getRutinaId() { return rutinaId; }
    public void setRutinaId(Long rutinaId) { this.rutinaId = rutinaId; }
    public Integer getDuracionRealMin() { return duracionRealMin; }
    public void setDuracionRealMin(Integer duracionRealMin) { this.duracionRealMin = duracionRealMin; }
}