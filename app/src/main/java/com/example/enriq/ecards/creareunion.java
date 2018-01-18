package com.example.enriq.ecards;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class creareunion extends AppCompatActivity {

    TextView tv,tve;
    Calendar Date,Time;
    int day,month,year,hour,minute;
    String format;
    Spinner mySpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creareunion);

        //Fecha

        tv = (TextView) findViewById(R.id.fecha);

        Date = Calendar.getInstance();

        day = Date.get(Calendar.DAY_OF_MONTH);
        month = Date.get(Calendar.MONTH);
        year = Date.get(Calendar.YEAR);

        month = month + 1;

        tv.setText(day + "/" + month + "/" + year);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(creareunion.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        tv.setText(dayOfMonth + "/" + monthOfYear + "/" + year);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        //Hora

        tve = (TextView) findViewById(R.id.hora);

        Time = Calendar.getInstance();

        hour = Time.get(Calendar.HOUR_OF_DAY);
        minute = Time.get(Calendar.MINUTE);

        selectedTimeFormat(hour);

        tve.setText(hour + " : " + minute + " " + format);

        tve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(creareunion.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        tve.setText(hourOfDay + " : " + minute + " " + format);

                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        //Spinner

        mySpinner = (Spinner) findViewById(R.id.asistentes);

        List<String> list = new ArrayList<>();
        list.add("Valentina");
        list.add("Laura");
        list.add("Enrique");
        list.add("Ronald");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mySpinner.setAdapter(adapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String itemvalue = parent.getItemAtPosition(position).toString();

                Toast.makeText(creareunion.this, "Selected: " + itemvalue, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Hora
    public void selectedTimeFormat(int hour){

        if (hour == 0){
            hour += 12;
            format = "AM";
        } else if (hour == 12){
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
    }
}
