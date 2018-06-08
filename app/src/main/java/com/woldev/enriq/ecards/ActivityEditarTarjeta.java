package com.woldev.enriq.ecards;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class ActivityEditarTarjeta extends AppCompatActivity {
    int day, month, year, hour, minute;

    TextView fecha, hora;
    Calendar Date, Time;
    MaterialSpinner tipo_tarea, proyecto;
    CheckBox tiempoObligatorio , LinkEvidencia;

    Button EditarTarjeta;
    TextInputEditText Titulo, Fechaentrega, LinkAyuda, Descripcion , Horas;
    TextInputLayout TILTitulo,TILFechaentrega, TILDescripcion, TILHoras;

    List<String> Proyectos = new ArrayList<>();
    List<String> Tareas = new ArrayList<>();

    List<String> tareaItem = new ArrayList<>();
    List<String> proyectoItem = new ArrayList<>();
    ArrayAdapter<String>  tareaAdapter, proyectoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarjeta);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Editar Tarjeta");


        TILTitulo = (TextInputLayout) findViewById(R.id.campo_titulo);
        Titulo = (TextInputEditText) findViewById(R.id.titulo);

        TILFechaentrega = (TextInputLayout) findViewById(R.id.fecha_e);
        Fechaentrega = (TextInputEditText) findViewById(R.id.EDTfecha);

        LinkAyuda = (TextInputEditText) findViewById(R.id.link);

        TILDescripcion = (TextInputLayout) findViewById(R.id.campo_descripcion);
        Descripcion = (TextInputEditText) findViewById(R.id.descripcion);

        LinkEvidencia = (CheckBox) findViewById(R.id.evidencia);
        tiempoObligatorio = (CheckBox) findViewById(R.id.horasduracion);

        TILHoras = (TextInputLayout) findViewById(R.id.tiempo_e);
        Horas = (TextInputEditText) findViewById(R.id.EDThora);

        EditarTarjeta = (Button) findViewById(R.id.BTNEditarTarjeta);

        //Fecha
        Date = Calendar.getInstance();
        day = Date.get(Calendar.DAY_OF_MONTH);
        month = Date.get(Calendar.MONTH);
        year = Date.get(Calendar.YEAR);

        //tv.setText(day + "/" + month + "/" + year);
        Fechaentrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityEditarTarjeta.this, new DatePickerDialog.OnDateSetListener() {
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

        tiempoObligatorio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                TILHoras.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });



        tipo_tarea = (MaterialSpinner) findViewById(R.id.tipotarea);
        proyecto = (MaterialSpinner) findViewById(R.id.proyecto);

        tareaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tareaItem);
        proyectoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, proyectoItem);

        tareaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proyectoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tipo_tarea.setAdapter(tareaAdapter);
        proyecto.setAdapter(proyectoAdapter);

        List<String> list = new ArrayList<>();
        list.add("Bitmoney");
        list.add("Ecotarjetas");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proyecto.setAdapter(adapter);


        List<String> list2 = new ArrayList<>();
        list2.add("Fronted");
        list2.add("Backed");
        list2.add("Diseño");


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo_tarea.setAdapter(adapter);





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
