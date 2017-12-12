package com.example.enriq.ecards;

/**
 * Created by Laura on 7/12/2017.
 */

/*Se supone que declaro las cosas que voy a utilizar en la tarjeta como variables normales, aparte de lo que hay
* en la vista, las voy a utilizar en el Adaptador y de esta manera conectar lo que me llegue a ellas desde la base
* de datos con las vistas*/

public class Fuente {

    String titulo,tipo,tiempo_e,tiempo_r,color;

    public Fuente(String titulo, String tipo, String tiempo_e, String tiempo_r, String color) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.tiempo_e = tiempo_e;
        this.tiempo_r = tiempo_r;
        this.color = color;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
