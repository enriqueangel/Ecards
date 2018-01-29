package com.example.enriq.ecards;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CrearReunion extends AppCompatActivity {

    int day, month, year, hour, minute;

    TextView tv, tve;
    Calendar Date, Time;
    String format;
    Spinner mySpinner;
    Button CrearReunion;
    EditText Titulo, Lugar, Descripcion;
    TextInputLayout TILTitulo, TILLugar;

    ExpandableListAdapter listAdapter;
    ExpandableListView listView;
    List<String> listDataHeader;
    HashMap<String, List<ItemListCheckbox>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_reunion);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Crear Reunion");

        listView = (ExpandableListView) findViewById(R.id.usuarios);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        listView.setAdapter(listAdapter);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                setListViewHeight(expandableListView, i);
                return false;
            }
        });

        CrearReunion = (Button)  findViewById(R.id.BTNCrearRunion);
        Titulo= (EditText)  findViewById(R.id.EDTtitulo);
        Lugar= (EditText)  findViewById(R.id.EDTlugar);
        Descripcion= (EditText)  findViewById(R.id.EDTdescripcion);
        TILTitulo= (TextInputLayout)  findViewById(R.id.CampoTitulo);
        TILLugar= (TextInputLayout)  findViewById(R.id.CampoLugar);


        CrearReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if  (!MetodosGlobales.validarTelefono(Titulo.getText().toString())){
                    TILTitulo.setError("Ingrese un Titulo");
                    //Toast.makeText(com.example.enriq.ecards.CrearReunion.this, "Ingrese un Titulo", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    TILTitulo.setError(null);
                }
                if  (!MetodosGlobales.validarTelefono(Lugar.getText().toString())){
                    TILLugar.setError("Ingrese un lugar");
                    //Toast.makeText(com.example.enriq.ecards.CrearReunion.this, "Ingrese un lugar" , Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    TILLugar.setError(null);
                }
                EnviarDatosWS();
            }
        });

        //Fecha
        tv = (TextView) findViewById(R.id.EDTfecha);
        Date = Calendar.getInstance();
        day = Date.get(Calendar.DAY_OF_MONTH);
        month = Date.get(Calendar.MONTH);
        year = Date.get(Calendar.YEAR);

        month = month + 1;
        //tv.setText(day + "/" + month + "/" + year);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CrearReunion.this, new DatePickerDialog.OnDateSetListener() {
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
        tve = (TextView) findViewById(R.id.EDThora);
        Time = Calendar.getInstance();
        hour = Time.get(Calendar.HOUR_OF_DAY);
        minute = Time.get(Calendar.MINUTE);
        selectedTimeFormat(hour);
        //tve.setText(hour + " : " + minute + " " + format);
        tve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CrearReunion.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourOfDay = selectedTimeFormat(hourOfDay);
                        String horaFormateada = (hourOfDay < 10)? String.valueOf("0" + hourOfDay) : String.valueOf(hourOfDay);
                        String minutoFormateada = (minute < 10)? String.valueOf("0" + minute) : String.valueOf(minute);
                        tve.setText(horaFormateada + ":" + minutoFormateada + " " + format);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
    }


    //ExpandableListView
    private void prepareListData(){
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        //Agrego cabecera principal
        listDataHeader.add("Usuarios");

        //Agrego cabecera de opciones
        List<ItemListCheckbox> usuarios = new ArrayList<>();
        usuarios.add(new ItemListCheckbox("Enrique Angel", "alsdjflkasdf", true));
        usuarios.add(new ItemListCheckbox("Valentina Rojas", "alsdjflkasdf", false));
        usuarios.add(new ItemListCheckbox("Ronal Gonzales", "alsdjflkasdf", true));
        usuarios.add(new ItemListCheckbox("Laura Gonzales", "alsdjflkasdf", false));

        listDataChild.put(listDataHeader.get(0), usuarios);
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

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    //Hora
    public int selectedTimeFormat(int hour){
        if (hour == 0){
            format = "AM";
            return hour += 12;
        } else if (hour == 12){
            format = "PM";
            return hour;
        } else if (hour > 12) {
            format = "PM";
            hour -= 12;
            return hour;
        } else {
            format = "AM";
            return hour;
        }
    }

    private void EnviarDatosWS(){

    }
}
