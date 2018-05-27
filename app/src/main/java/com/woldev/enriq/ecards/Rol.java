package com.woldev.enriq.ecards;

import android.content.Context;

/**
 * Created by enriq on 5/01/2018.
 */

public class Rol {
    private String text;
    private int image;
    private String id;
    private Context Contexto;

    Rol(String text, int imagen, String id, Context Contexto){
        this.text = text;
        this.image = imagen;
        this.id = id;
        this.Contexto = Contexto;
    }

    public String getText(){
        return this.text;
    }

    int getImage(){
        return this.image;
    }

    public String getId(){
        return this.id;
    }

    public Context getContexto() {
        return Contexto;
    }

    public void setContexto(Context contexto) {
        Contexto = contexto;
    }
}
