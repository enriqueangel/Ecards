package com.woldev.enriq.ecards;

/**
 * Created by jose on 18/01/2018.
 */

public abstract class MetodosGlobales {


    public static boolean validarCampoVacio(String texto){
        if (texto.equals("")){

            return false;
        } else {

            return true;
        }
    }

}
