package com.example.enriq.ecards;

import org.json.JSONObject;

/**
 * Created by Laura on 8/02/2018.
 */

public class Fuente_Notificaciones {
    String Titulo;
    String Descripcion;
    String Fecha;
    String Hora;
    JSONObject DATOS;

    public Fuente_Notificaciones(String titulo, String descripcion, String fecha, String hora) {
        Titulo = titulo;
        Descripcion = descripcion;
        Fecha = fecha;
        Hora = hora;
    }

    public JSONObject getDATOS() {
        return DATOS;
    }

    public void setDATOS(JSONObject DATOS) {
        this.DATOS = DATOS;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }
}
