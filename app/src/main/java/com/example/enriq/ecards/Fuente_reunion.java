package com.example.enriq.ecards;

import java.util.Date;

/**
 * Created by Laura on 14/12/2017.
 */

public class Fuente_reunion {

    String Titulo,Fecha,Lugar,Hora,Color;


    public Fuente_reunion(String titulo, String lugar, String hora, String color,String fecha) {
        Titulo = titulo;
        Lugar = lugar;
        Hora = hora;

        Color = color;
        Fecha = fecha;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getLugar() {
        return Lugar;
    }

    public void setLugar(String lugar) {
        Lugar = lugar;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }
}
