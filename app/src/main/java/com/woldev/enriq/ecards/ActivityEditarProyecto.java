package com.woldev.enriq.ecards;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class ActivityEditarProyecto extends AppCompatActivity {

    int day, month, year;
    Calendar Date;
    EditText Cliente, Descripcion ,Fecha;
    TextInputLayout TILFecha;
    Button CrearProyecto;
    MaterialSpinner lider;

    List<String> estadoItem = new ArrayList<>();
    List<String> liderItem = new ArrayList<>();
    ArrayAdapter<String> estadoAdapter, liderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_proyecto);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Editar Proyecto");

        Fecha= (EditText)  findViewById(R.id.EDTfecha);
        Descripcion= (EditText)  findViewById(R.id.descripcion);
        Cliente = (EditText)  findViewById(R.id.cliente);
        CrearProyecto = (Button)  findViewById(R.id.BTNCrearProyecto);
        TILFecha= (TextInputLayout)  findViewById(R.id.CampoFecha);

        //Fecha
        Date = Calendar.getInstance();
        day = Date.get(Calendar.DAY_OF_MONTH);
        month = Date.get(Calendar.MONTH);
        year = Date.get(Calendar.YEAR);

        //tv.setText(day + "/" + month + "/" + year);
        Fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityEditarProyecto.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        String diaFormateada = (dayOfMonth < 10)? String.valueOf("0" + dayOfMonth) : String.valueOf(dayOfMonth);
                        String mesFormateada = (monthOfYear < 10)? String.valueOf("0" + monthOfYear) : String.valueOf(monthOfYear);
                        Fecha.setText(diaFormateada + "/" + mesFormateada + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        lider = (MaterialSpinner) findViewById(R.id.lider);
        liderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, liderItem);
        liderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lider.setAdapter(liderAdapter);

        List<String> list = new ArrayList<>();
        list.add("Valentina Rojas");
        list.add("Laura Gonzalez");
        list.add("Enrique");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lider.setAdapter(adapter);
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
