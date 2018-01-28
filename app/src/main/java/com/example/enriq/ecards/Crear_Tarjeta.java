package com.example.enriq.ecards;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Laura on 9/01/2018.
 */

public class Crear_Tarjeta extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int day, month, year, hour, minute;

    TextView fecha, hora;
    Calendar Date, Time;
    MaterialSpinner responsable, tester, tipo_tarea, proyecto;
    CheckBox tiempoObligatorio;
    TextInputLayout campoHoras;

    List<String> responItem = new ArrayList<>();
    List<String> testerItem = new ArrayList<>();
    List<String> tareaItem = new ArrayList<>();
    List<String> proyectoItem = new ArrayList<>();
    ArrayAdapter<String> responAdapter, testerAdapter, tareaAdapter, proyectoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarjeta);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Crear Tarjeta");

        tiempoObligatorio = (CheckBox) findViewById(R.id.horasduracion);
        campoHoras = (TextInputLayout) findViewById(R.id.tiempo_e);

        tiempoObligatorio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                campoHoras.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });

        //Fecha
        fecha = (TextView) findViewById(R.id.EDTfecha);
        Date = Calendar.getInstance();
        day = Date.get(Calendar.DAY_OF_MONTH);
        month = Date.get(Calendar.MONTH);
        year = Date.get(Calendar.YEAR);

        month = month + 1;
        //tv.setText(day + "/" + month + "/" + year);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Crear_Tarjeta.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        fecha.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //Hora
        hora = (TextView) findViewById(R.id.EDThora);
        Time = Calendar.getInstance();
        hour = Time.get(Calendar.HOUR_OF_DAY);
        minute = Time.get(Calendar.MINUTE);
        //tve.setText(hour + " : " + minute + " " + format);
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Crear_Tarjeta.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String horaFormateada = (hourOfDay < 10)? String.valueOf("0" + hourOfDay) : String.valueOf(hourOfDay);
                        String minutoFormateada = (minute < 10)? String.valueOf("0" + minute) : String.valueOf(minute);
                        hora.setText(horaFormateada + ":" + minutoFormateada);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
        
        inicializarItems();
        responsable = (MaterialSpinner) findViewById(R.id.encargado);
        tester = (MaterialSpinner) findViewById(R.id.tester);
        tipo_tarea = (MaterialSpinner) findViewById(R.id.tipotarea);
        proyecto = (MaterialSpinner) findViewById(R.id.proyecto);

        responAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, responItem);
        testerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, testerItem);
        tareaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tareaItem);
        proyectoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, proyectoItem);

        responAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tareaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proyectoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        responsable.setAdapter(responAdapter);
        tester.setAdapter(testerAdapter);
        tipo_tarea.setAdapter(tareaAdapter);
        proyecto.setAdapter(proyectoAdapter);

        responsable.setOnItemSelectedListener(this);
        tester.setOnItemSelectedListener(this);
        tipo_tarea.setOnItemSelectedListener(this);
        proyecto.setOnItemSelectedListener(this);
    }

    private void inicializarItems() {
        responItem.add("Valentina Rojas");
        responItem.add("Enrique Angel");
        responItem.add("Ronal Gonazales");
        responItem.add("Laura Gonzales");

        testerItem.add("Valentina Rojas");
        testerItem.add("Enrique Angel");
        testerItem.add("Ronal Gonazales");
        testerItem.add("Laura Gonzales");

        tareaItem.add("Documentacion");
        tareaItem.add("Backend");
        tareaItem.add("Frontend");
        tareaItem.add("Diseño");

        proyectoItem.add("Tarjetas");
        proyectoItem.add("Aguilas");
        proyectoItem.add("Matematicas");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Intent i = new Intent(Crear_Tarjeta.this, MenuAdmin.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String itemvalue = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(Crear_Tarjeta.this, "Selected: " + itemvalue, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

