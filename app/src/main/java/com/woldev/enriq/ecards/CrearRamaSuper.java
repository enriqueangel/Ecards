package com.woldev.enriq.ecards;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class CrearRamaSuper extends AppCompatActivity {

    TextInputEditText NombreRama, Codigo;
    TextInputLayout TILNombreRama,TILCodigo;

    MaterialSpinner lider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rama_super);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Crear Rama");



        TILNombreRama = (TextInputLayout) findViewById(R.id.campo_nombrerama);
        NombreRama = (TextInputEditText) findViewById(R.id.nombrer);

        TILCodigo = (TextInputLayout) findViewById(R.id.campo_cod);
        Codigo= (TextInputEditText) findViewById(R.id.cod);

       lider = (MaterialSpinner) findViewById(R.id.slider);

       List<String> list = new ArrayList<>();
       list.add("Leonardo");
       list.add("Angel");
       list.add("Valentina");

       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lider.setAdapter(adapter);
        lider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String itemvalue = parent.getItemAtPosition(position).toString();

                Toast.makeText(CrearRamaSuper.this, "Selected: " + itemvalue,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }
}
