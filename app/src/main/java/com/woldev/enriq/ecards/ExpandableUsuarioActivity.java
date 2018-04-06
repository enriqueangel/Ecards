package com.woldev.enriq.ecards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableUsuarioActivity extends AppCompatActivity {

    ExpanListAdapterUsuario listAdapter;
    ExpandableListView listView;
    List<String> listDataHeader;
    HashMap<String, List<Usuario>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_usuario);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Usuarios");

        listView = (ExpandableListView) findViewById(R.id.usuarios);
        cargarDatos();
        listAdapter = new ExpanListAdapterUsuario(getApplication(), listDataHeader, listDataChild);
        listView.setAdapter(listAdapter);
    }

    private void cargarDatos() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listDataHeader.add("Movil");
        listDataHeader.add("Juegos");

        JSONObject prueba = new JSONObject();

        List<Usuario> movil = new ArrayList<>();
        movil.add(new Usuario(R.drawable.fondo_perfil, "Enrique Angel", "Horas Laborales:", "Horas Trabajadas:", "00:00", "00:00", prueba));
        movil.add(new Usuario(R.drawable.fondo_perfil, "Valentina Rojas", "Horas Laborales:", "Horas Trabajadas:", "00:00", "00:00", prueba));
        movil.add(new Usuario(R.drawable.fondo_perfil, "Laura Gonzalez", "Horas Laborales:", "Horas Trabajadas:", "00:00", "00:00", prueba));

        List<Usuario> juegos = new ArrayList<>();
        juegos.add(new Usuario(R.drawable.fondo_perfil, "Enrique Angel", "Horas Laborales:", "Horas Trabajadas:", "00:00", "00:00", prueba));
        juegos.add(new Usuario(R.drawable.fondo_perfil, "Valentina Rojas", "Horas Laborales:", "Horas Trabajadas:", "00:00", "00:00", prueba));
        juegos.add(new Usuario(R.drawable.fondo_perfil, "Laura Gonzalez", "Horas Laborales:", "Horas Trabajadas:", "00:00", "00:00", prueba));

        listDataChild.put(listDataHeader.get(0), movil); // Header, Child data
        listDataChild.put(listDataHeader.get(1), juegos);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
