package com.woldev.enriq.ecards;

/**
 * Created by enriq on 30/01/2018.
 */

public class UsuarioCorte {
    private String nombre;
    private String hora;

    public UsuarioCorte(String nombre, String hora) {
        this.nombre = nombre;
        this.hora = hora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
