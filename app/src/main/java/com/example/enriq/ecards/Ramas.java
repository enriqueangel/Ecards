package com.example.enriq.ecards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class Ramas extends AppCompatActivity {

    ArrayList<ElementoLista> ramas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramas);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Proyectos");

        ramas = new ArrayList<ElementoLista>();
        ramas.add(new ElementoLista("Tarjetas", "aaa"));
        ramas.add(new ElementoLista("Metrocable", "aaa"));
        ramas.add(new ElementoLista("Aguilas", "aaa"));
        ramas.add(new ElementoLista("Otro", "aaa"));

        ListView list = (ListView) findViewById(R.id.lista);
        ElementoListaAdapter adapter = new ElementoListaAdapter(this, ramas);
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
