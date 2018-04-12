package com.woldev.enriq.ecards;

import org.json.JSONObject;

/**
 * Created by enriq on 1/02/2018.
 */

public class ElementoLista {
    String nombre;
    String id;
    JSONObject datos;

    public ElementoLista(String nombre, String id, JSONObject datos) {
        this.nombre = nombre;
        this.id = id;
        this.datos = datos;
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

    public JSONObject getDatos() {
        return datos;
    }

    public void setDatos(JSONObject datos) {
        this.datos = datos;
    }
}
