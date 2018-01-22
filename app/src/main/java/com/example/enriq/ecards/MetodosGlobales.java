package com.example.enriq.ecards;

/**
 * Created by jose on 18/01/2018.
 */

public abstract class MetodosGlobales {


    public static boolean validarTelefono(String texto){
        if (texto.equals("")){

            return false;
        } else {

            return true;
        }
    }

}
