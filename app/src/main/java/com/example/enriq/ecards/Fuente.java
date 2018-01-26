package com.example.enriq.ecards;

import org.json.JSONObject;

/**
 * Created by Laura on 7/12/2017.
 */

/*Se supone que declaro las cosas que voy a utilizar en la tarjeta como variables normales, aparte de lo que hay
* en la vista, las voy a utilizar en el Adaptador y de esta manera conectar lo que me llegue a ellas desde la base
* de datos con las vistas*/

public class Fuente {

    String titulo;
    String tipo;
    String tiempo_e;
    String tiempo_r;
    String version;
    int color;
    Boolean IsReunion;
    JSONObject DATOS;

    public Fuente(String titulo, String tipo, String tiempo_e, String tiempo_r, String version, int color,boolean isReunion,JSONObject DATOS) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.tiempo_e = tiempo_e;
        this.tiempo_r = tiempo_r;
        this.version = version;
        this.color = color;
        this.IsReunion = isReunion;
        this.DATOS = DATOS;
    }

    public JSONObject getDATOS() {
        return DATOS;
    }

    public void setDATOS(JSONObject DATOS) {
        this.DATOS = DATOS;
    }



    public String getTitulo() {
        return titulo;
    }

    public Boolean getReunion() {
        return IsReunion;
    }

    public void setReunion(Boolean reunion) {
        IsReunion = reunion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTiempo_e() {
        return tiempo_e;
    }

    public void setTiempo_e(String tiempo_e) {
        this.tiempo_e = tiempo_e;
    }

    public String getTiempo_r() {
        return tiempo_r;
    }

    public void setTiempo_r(String tiempo_r) {
        this.tiempo_r = tiempo_r;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
