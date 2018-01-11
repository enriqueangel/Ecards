package com.example.enriq.ecards;

/**
 * Created by enriq on 5/01/2018.
 */

public class Rol {
    private String text;
    private int image;
    private String id;

    public Rol (String text, int imagen, String id){
        this.text = text;
        this.image = imagen;
        this.id = id;
    }

    public String getText(){
        return this.text;
    }

    public int getImage(){
        return this.image;
    }

    public String getId(){
        return this.id;
    }
}
