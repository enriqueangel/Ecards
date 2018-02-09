package com.example.enriq.ecards;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Laura on 1/02/2018.
 */

public class Crear_Tester extends AppCompatActivity {

    int day, month, year;
    Calendar Date;

    TextInputEditText Fechaentrega;
    TextInputLayout TILFechaentrega;

    MaterialSpinner responsable, proyecto;

    List<String> responItem = new ArrayList<>();
    List<String> proyectoItem = new ArrayList<>();
    ArrayAdapter<String> responAdapter, proyectoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tester);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Crear Tester");

        TILFechaentrega = (TextInputLayout) findViewById(R.id.fecha_e);
        Fechaentrega = (TextInputEditText) findViewById(R.id.EDTfecha);

        //Fecha
        Date = Calendar.getInstance();
        day = Date.get(Calendar.DAY_OF_MONTH);
        month = Date.get(Calendar.MONTH);
        year = Date.get(Calendar.YEAR);

        month = month + 1;
        //tv.setText(day + "/" + month + "/" + year);
        Fechaentrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Crear_Tester.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        String diaFormateada = (dayOfMonth < 10)? String.valueOf("0" + dayOfMonth) : String.valueOf(dayOfMonth);
                        String mesFormateada = (monthOfYear < 10)? String.valueOf("0" + monthOfYear) : String.valueOf(monthOfYear);
                        Fechaentrega.setText(diaFormateada + "/" + mesFormateada + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        CargarDatos();

        responsable = (MaterialSpinner) findViewById(R.id.encargado);
        proyecto = (MaterialSpinner) findViewById(R.id.proyecto);

        responAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, responItem);
        proyectoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, proyectoItem);

        responAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proyectoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        responsable.setAdapter(responAdapter);
        proyecto.setAdapter(proyectoAdapter);
    }

    private void CargarDatos() {
        responItem.add("Enrique Angel");
        responItem.add("Valentina Rojas");
        responItem.add("Ronald Gonzalez");
        responItem.add("Laura Gonzales");

        proyectoItem.add("Tarjetas");
        proyectoItem.add("Matematicas");
        proyectoItem.add("Metrocable");
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
