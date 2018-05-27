package com.woldev.enriq.ecards;

import android.app.Application;



public class VariablesGlobales  extends Application {

    private String Areas;
    private String TipoUser;

    public String getAreas() {
        return Areas;
    }

    public void setAreas(String areas) {
        Areas = areas;
    }

    public String getTipoUser() {
        return TipoUser;
    }

    public void setTipoUser(String tipoUser) {
        TipoUser = tipoUser;
    }
}
