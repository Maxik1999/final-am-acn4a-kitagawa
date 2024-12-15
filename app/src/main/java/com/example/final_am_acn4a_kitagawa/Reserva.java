package com.example.final_am_acn4a_kitagawa;

public class Reserva {
    private String restaurante;
    private String fecha;
    private String hora;
    private int comensales;
    private String usuarioId;

    // Constructor vacío necesario para Firebase
    public Reserva() {}

    // Getters y setters
    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getComensales() {
        return comensales;
    }

    public void setComensales(int comensales) {
        this.comensales = comensales;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "restaurante='" + restaurante + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", comensales=" + comensales +
                ", usuarioId='" + usuarioId + '\'' +
                '}';
    }
}
