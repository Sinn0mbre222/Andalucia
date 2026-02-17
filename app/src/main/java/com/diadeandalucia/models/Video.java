package com.diadeandalucia.models;

public class Video {
    private String titulo;
    private int videoResId; // El ID del archivo en res/raw

    public Video(String titulo, int videoResId) {
        this.titulo = titulo;
        this.videoResId = videoResId;
    }

    public String getTitulo() { return titulo; }
    public int getVideoResId() { return videoResId; }
}