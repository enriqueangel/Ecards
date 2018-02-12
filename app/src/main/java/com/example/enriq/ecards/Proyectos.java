package com.example.enriq.ecards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class Proyectos extends AppCompatActivity {

    ArrayList<ElementoLista> proyectos ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyectos);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Proyectos");

        proyectos = new ArrayList<ElementoLista>();
        proyectos.add(new ElementoLista("Tarjetas", "aaa"));
        proyectos.add(new ElementoLista("Metrocable", "aaa"));
        proyectos.add(new ElementoLista("Aguilas", "aaa"));
        proyectos.add(new ElementoLista("Otro", "aaa"));

        ListView list = (ListView) findViewById(R.id.lista);
        ElementoListaAdapter adapter = new ElementoListaAdapter(this, proyectos);
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
