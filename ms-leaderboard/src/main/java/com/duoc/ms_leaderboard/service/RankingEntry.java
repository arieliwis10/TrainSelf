package com.duoc.ms_leaderboard.service;

public class RankingEntry {
    private int posicion;
    private Long usuarioId;
    private String nombre;
    private int puntos;

    public RankingEntry(int posicion, Long usuarioId, String nombre, int puntos) {
        this.posicion = posicion;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public int getPosicion() { return posicion; }
    public Long getUsuarioId() { return usuarioId; }
    public String getNombre() { return nombre; }
    public int getPuntos() { return puntos; }
}