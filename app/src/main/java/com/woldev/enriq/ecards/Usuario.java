package com.woldev.enriq.ecards;

import android.net.Uri;

import org.json.JSONObject;

import java.net.URI;


/**
 * Created by ValentinaR on 22/01/2018.
 */

public class Usuario {
    private String nombre;
    private String foto;
    private String horasl;
    private String horast;
    private String hl;
    private String ht;
    JSONObject DATOS;

     Usuario(String image, String nombre, String horasl, String horast, String hl, String ht, JSONObject DATOS) {
        this.nombre = nombre;
        this.foto = foto;
        this.horasl = horasl;
        this.horast = horast;
        this.hl = hl;
        this.ht = ht;
        this.DATOS = DATOS;
    }

    public JSONObject getDATOS() {
        return DATOS;
    }

    public void setDATOS(JSONObject DATOS) {
        this.DATOS = DATOS;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {this.nombre = nombre;}


    public String getFoto() { return foto; }

    public void setFoto(String foto) { this.foto = foto; }



    String getHorasl() {
        return horasl;
    }

    public void setHorasl(String horasl) {
        this.horasl = horasl;
    }



    String getHorast() {return horast;}

    public void setHorast(String horast) {
        this.horast = horast;
    }



    public String getHl() {return hl;}

    public void setHl(String hl) {this.hl = hl;}



    public String getHt() {return ht;}

    public void setHt(String ht) {this.ht = ht;}
}