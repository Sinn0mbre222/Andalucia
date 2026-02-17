package com.diadeandalucia.models;
public class Personaje {
    private String nombre;
    private String descripcion;
    private int imagenResId; // ID del drawable

    public Personaje(String nombre, String descripcion, int imagenResId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenResId = imagenResId;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public int getImagenResId() { return imagenResId; }
}