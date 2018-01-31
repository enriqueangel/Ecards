package com.example.enriq.ecards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Corte extends AppCompatActivity {

    ExpanListAdapterCorte listAdapter;
    ExpandableListView listView;
    List<String> listDataHeader;
    HashMap<String, List<UsuarioCorte>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corte);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Corte");


        listView = (ExpandableListView) findViewById(R.id.ramas);
        prepareListData();
        listAdapter = new ExpanListAdapterCorte(this, listDataHeader, listDataChild);
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                //Intent i = new Intent(CrearReunion.this, MenuAdmin.class);
                //startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        listDataHeader.add("Sin problemas");
        listDataHeader.add("Con problemas");

        // Adding child data
        List<UsuarioCorte> top250 = new ArrayList<>();
        top250.add(new UsuarioCorte("Valentina Rojas", "18:30"));
        top250.add(new UsuarioCorte("Enrique Angel", "00:00"));
        top250.add(new UsuarioCorte("Ronal Gonzales", "03:00"));
        top250.add(new UsuarioCorte("Laura Gonzales", "12:45"));

        List<UsuarioCorte> nowShowing = new ArrayList<>();
        nowShowing.add(new UsuarioCorte("Laura Galenano", "10:00"));
        nowShowing.add(new UsuarioCorte("Daniela Ramirez", "08:00"));

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
    }


}
