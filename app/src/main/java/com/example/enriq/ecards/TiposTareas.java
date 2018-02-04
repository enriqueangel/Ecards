package com.example.enriq.ecards;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class TiposTareas extends AppCompatActivity {

    FloatingActionButton agregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipos_tareas);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Tipos Tareas");

        ArrayList<TipoTarea> tareas = new ArrayList<TipoTarea>();
        tareas.add(new TipoTarea("Backend", "aaaa"));
        tareas.add(new TipoTarea("Frontend", "aaaa"));
        tareas.add(new TipoTarea("Diseño", "aaaa"));

        ListView list = (ListView) findViewById(R.id.lista);
        TipoTareaAdapter adapter = new TipoTareaAdapter(this, tareas);
        list.setAdapter(adapter);

        agregar = (FloatingActionButton) findViewById(R.id.agregar);
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
