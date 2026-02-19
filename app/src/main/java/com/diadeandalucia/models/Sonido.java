package com.diadeandalucia.models;

public class Sonido {
    private String titulo;
    private int resId;

    public Sonido(String titulo, int resId) {
        this.titulo = titulo;
        this.resId = resId;
    }

    public String getTitulo() { return titulo; }
    public int getResId() { return resId; }
}