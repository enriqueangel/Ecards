package com.example.enriq.ecards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class UsuariosNuevos extends AppCompatActivity {

    ArrayList<ElementoLista> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_nuevos);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Proyectos");

        usuarios = new ArrayList<ElementoLista>();
        usuarios.add(new ElementoLista("Ronald Gonzales", "aaa"));
        usuarios.add(new ElementoLista("Enrique Angel", "aaa"));
        usuarios.add(new ElementoLista("Valentina Rojas", "aaa"));
        usuarios.add(new ElementoLista("Laura Gonzales", "aaa"));

        ListView list = (ListView) findViewById(R.id.lista);
        ElementoListaAdapter adapter = new ElementoListaAdapter(this, usuarios);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
