package com.woldev.enriq.ecards;

/**
 * Created by enriq on 28/01/2018.
 */

public class ItemListCheckbox {
    private String nombre;
    private String id;
    private boolean check;

    public ItemListCheckbox(String nombre, String id, boolean check) {
        this.nombre = nombre;
        this.id = id;
        this.check = check;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
