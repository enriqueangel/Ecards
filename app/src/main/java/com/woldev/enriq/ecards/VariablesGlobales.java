package com.woldev.enriq.ecards;

import android.app.Application;



public class VariablesGlobales  extends Application {

    private String Areas;
    private String TipoUser;
    private String UserID;
    private int CantRoles;

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

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getCantRoles() {
        return CantRoles;
    }

    public void setCantRoles(int cantRoles) {
        CantRoles = cantRoles;
    }
}
