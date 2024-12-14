package com.example.final_am_acn4a_kitagawa;

public class Restaurante {
    private String nombre;
    private int imagenId;

    public Restaurante(String nombre, int imagenId) {
        this.nombre = nombre;
        this.imagenId = imagenId;
    }

    public String getNombre() {
        return nombre;
    }

    public int getImagenId() {
        return imagenId;
    }
}
