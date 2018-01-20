package com.example.enriq.ecards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by enriq on 5/01/2018.
 */

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(toolbar);

        ArrayList<Rol> items = new ArrayList<Rol>();
        String Areas = getIntent().getStringExtra("Areas");

        try {
            JSONArray listaAreas = new JSONArray(Areas);
            for (int i = 0; i < listaAreas.length(); i++){
                JSONObject area = listaAreas.getJSONObject(i);
                String nombreTipo = area.get("tipo").toString();
                switch (nombreTipo){
                    case "empleado":
                        String empleado = "Empleado";
                        items.add(new Rol(empleado, R.drawable.ic_empleado, nombreTipo));
                        break;
                    case "administrador":
                        String administrador = "Administrador";
                        items.add(new Rol(administrador, R.drawable.ic_user, nombreTipo));
                        break;
                    case "lider":
                        String lider = "Lider";
                        items.add(new Rol(lider, R.drawable.ic_usuario, nombreTipo));
                        break;
                    case "super":
                        String super_u = "Super Usuario";
                        items.add(new Rol(super_u, R.drawable.ic_usuarios_multiples_en_silueta, nombreTipo));
                        break;
                    default:
                        break;
                }
            }

            RecyclerView contenedor = (RecyclerView) findViewById(R.id.contenedor);
            contenedor.setHasFixedSize(true);

            contenedor.setAdapter(new RolAdapter(items));
            contenedor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
