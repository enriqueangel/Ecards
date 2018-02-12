package com.example.enriq.ecards;

/**
 * Created by enriq on 1/02/2018.
 */

public class ElementoLista {
    String nombre;
    String id;

    public ElementoLista(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
