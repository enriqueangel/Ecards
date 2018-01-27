package com.example.enriq.ecards;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;


/**
 * Created by ValentinaR on 22/01/2018.
 */

public class Usuario {
    private String nombre;
    private int image;
    private String horasl;
    private String horast;
    private String hl;
    private String ht;
    JSONObject DATOS;

     Usuario(int image,String nombre, String horasl, String horast, String hl, String ht,JSONObject DATOS) {
        this.nombre = nombre;
        this.image = image;
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

    int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

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