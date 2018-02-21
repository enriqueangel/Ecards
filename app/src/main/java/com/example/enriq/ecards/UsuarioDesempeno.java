package com.example.enriq.ecards;

/**
 * Created by ValentinaR on 20/02/2018.
 */

public class UsuarioDesempeno {

    private String nombre;
    private String hora;
    private String descripcion;

    public UsuarioDesempeno(String nombre, String hora, String descripcion) {
        this.nombre = nombre;
        this.hora = hora;
        this.descripcion = descripcion;
    }

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getHora() {return hora;}

    public void setHora(String hora) {this.hora = hora;}

    public String getDescripcion() {return descripcion;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
}
