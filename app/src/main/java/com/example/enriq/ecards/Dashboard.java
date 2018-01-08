package com.example.enriq.ecards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by enriq on 5/01/2018.
 */

public class Dashboard extends AppCompatActivity {

    private RecyclerView contenedor;

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
                JSONObject tipo = area.getJSONObject("tipo");
                String nombreTipo = tipo.get("tipo").toString();
                switch (nombreTipo){
                    case "empleado":
                        items.add(new Rol("Empleado", R.drawable.ic_empleado, nombreTipo));
                        break;
                    case "administrador":
                        items.add(new Rol("Administrador", R.drawable.ic_user, nombreTipo));
                        break;
                    case "lider":
                        items.add(new Rol("Lider", R.drawable.ic_usuario, nombreTipo));
                        break;
                    case "super":
                        items.add(new Rol("Super Usuario", R.drawable.ic_usuarios_multiples_en_silueta, nombreTipo));
                        break;
                    default:
                        break;
                }
            }

            contenedor = (RecyclerView) findViewById(R.id.contenedor);
            contenedor.setHasFixedSize(true);

            contenedor.setAdapter(new RolAdapter(items));
            contenedor.setLayoutManager(new LinearLayoutManager(getApplication()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
