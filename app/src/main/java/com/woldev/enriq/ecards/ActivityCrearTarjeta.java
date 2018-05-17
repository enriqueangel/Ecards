package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Laura on 9/01/2018.
 */

public class ActivityCrearTarjeta extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int day, month, year, hour, minute;

    JSONArray JSONUsuarios;
    JSONArray JSONproyectos;
    JSONArray JSONtareas;

    String url ;
    RequestQueue requestQueue;

    ExpandableUsuariosFragment fragmento1 = new ExpandableUsuariosFragment();
    SpinnerUsuariosFragment fragmento2 = new SpinnerUsuariosFragment();

    List<String> Proyectos = new ArrayList<>();
    List<String> Tareas = new ArrayList<>();

    String TIPOUSUARIO = "";

    TextView fecha, hora;
    Calendar Date, Time;
    MaterialSpinner  tipo_tarea, proyecto;
    CheckBox tiempoObligatorio , LinkEvidencia;

    Button CrearTarjeta;
    TextInputEditText Titulo, Fechaentrega, LinkAyuda, Descripcion , Horas;
    TextInputLayout TILTitulo,TILFechaentrega, TILDescripcion, TILHoras;

    List<String> tareaItem = new ArrayList<>();
    List<String> proyectoItem = new ArrayList<>();
    ArrayAdapter<String>  tareaAdapter, proyectoAdapter;

    AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarjeta);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Crear Tarjeta");

        TIPOUSUARIO = getIntent().getStringExtra("TIPO");

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

        CrearTarjeta = (Button) findViewById(R.id.BTNCrearTarjeta);

        url = getString(R.string.URLWS);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView2 = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
        dialog = mBuilder.create();

        tiempoObligatorio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                TILHoras.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });

        //Fecha
        Date = Calendar.getInstance();
        day = Date.get(Calendar.DAY_OF_MONTH);
        month = Date.get(Calendar.MONTH);
        year = Date.get(Calendar.YEAR);

        //tv.setText(day + "/" + month + "/" + year);
        Fechaentrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityCrearTarjeta.this, new DatePickerDialog.OnDateSetListener() {
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

        //Hora
        Time = Calendar.getInstance();
        hour = Time.get(Calendar.HOUR_OF_DAY);
        minute = Time.get(Calendar.MINUTE);
        //tve.setText(hour + " : " + minute + " " + format);
        Horas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityCrearTarjeta.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String horaFormateada = (hourOfDay < 10)? String.valueOf("0" + hourOfDay) : String.valueOf(hourOfDay);
                        String minutoFormateada = (minute < 10)? String.valueOf("0" + minute) : String.valueOf(minute);
                        Horas.setText(horaFormateada + ":" + minutoFormateada);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        CrearTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VerificarCampos()) {
                    EnviarDatosWS();
                }
            }
        });

        CargarDatos();

        tipo_tarea = (MaterialSpinner) findViewById(R.id.tipotarea);
        proyecto = (MaterialSpinner) findViewById(R.id.proyecto);

        tareaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tareaItem);
        proyectoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, proyectoItem);

        tareaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proyectoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tipo_tarea.setAdapter(tareaAdapter);
        proyecto.setAdapter(proyectoAdapter);

        /*
        responsable.setOnItemSelectedListener(this);
        tester.setOnItemSelectedListener(this);
        tipo_tarea.setOnItemSelectedListener(this);
        proyecto.setOnItemSelectedListener(this);
        */
    }

    private boolean VerificarCampos() {
        int Pos3 =  proyecto.getSelectedItemPosition();
        int Pos4 =  tipo_tarea.getSelectedItemPosition();

        if  (!MetodosGlobales.validarCampoVacio(Titulo.getText().toString())){
            TILTitulo.setError("Ingrese un Titulo");
            //Toast.makeText(com.woldev.enriq.ecards.CrearReunion.this, "Ingrese un Titulo", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            TILTitulo.setError(null);
        }

        if  (!MetodosGlobales.validarCampoVacio(Fechaentrega.getText().toString())){
            TILFechaentrega.setError("Ingrese una fecha");
            return false;
        }else{
            TILFechaentrega.setError(null);
        }

        if  (!MetodosGlobales.validarCampoVacio(Descripcion.getText().toString())){
            TILDescripcion.setError("Ingrese una descripcion");
            return false;
        }else{
            TILDescripcion.setError(null);
        }

        if (Pos3 == 0){
            proyecto.setError("Seleccione un proyecto.");
            return false;
        }else{
            proyecto.setError(null);
        }
        if (Pos4 == 0){
            tipo_tarea.setError("Seleccione un tipo de tarea.");
            return false;
        }else{
            tipo_tarea.setError(null);
        }

        if (TIPOUSUARIO.equals("SUPERUSER")){
            if (!fragmento1.verificarCampos()){
                return false;
            }
        }else{
            if (!fragmento2.verificarCampos()){
                return false;
            }
        }

        if (tiempoObligatorio.isChecked()){
            if  (!MetodosGlobales.validarCampoVacio(Horas.getText().toString())){
                TILHoras.setError("Ingrese las horas");
                return false;
            }else{
                TILHoras.setError(null);
            }
        }

        return true;

    }

    private void CargarDatos() {
        String urltemp = url+"informacion/crear_tarjeta";

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final AlertDialog dialog = mBuilder.create();

        requestQueue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap<String, String>();
        params.put("rol", TIPOUSUARIO);

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urltemp, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONUsuarios = response.getJSONArray("usuarios");
                            JSONproyectos = response.getJSONArray("proyectos");
                            JSONtareas = response.getJSONArray("tareas");

                            CargarUsuarios();
                            CargarProyectos();
                            CargarTareas();

                        } catch (JSONException e) {
                            Log.e("Volley", "Invalid JSON Object.");
                            Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.e("Volley", error.toString());
                        Toast.makeText(getApplicationContext(), "Error en la conexion", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            /*
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                SharedPreferences SP = getSharedPreferences("TOKEN",MODE_PRIVATE);
                String tokenTemp = SP.getString("token","");
                headers.put("token", tokenTemp);
                return headers;
            }
        };

        requestQueue.add(arrReq);
    }

    private void CargarTareas() throws JSONException {
        for (int i = 0; i < JSONtareas.length(); i++) {
            JSONObject row = JSONtareas.getJSONObject(i);
            String NombreTEmp = row.getString("tipo");
            String BDidTEmp = row.getString("_id");
            Tareas.add(BDidTEmp);
            tareaItem.add(NombreTEmp);
        }
    }

    private void CargarProyectos() throws JSONException {
        for (int i = 0; i < JSONproyectos.length(); i++) {
            JSONObject row = JSONproyectos.getJSONObject(i);
            String NombreTEmp = row.getString("nombre");
            String BDidTEmp = row.getString("_id");
            Proyectos.add(BDidTEmp);
            proyectoItem.add(NombreTEmp);
        }
    }

    private void CargarUsuarios() throws JSONException {
        Bundle args = new Bundle();
        args.putString("USUARIOS", JSONUsuarios.toString());

        fragmento1.setArguments(args);
        fragmento2.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (TIPOUSUARIO.equals("SUPERUSER")){
            transaction.replace(R.id.ContenedorCrearTarjeta, fragmento1);
        }else{
            transaction.replace(R.id.ContenedorCrearTarjeta, fragmento2);
        }
        transaction.commit();
    }

    private void EnviarDatosWS() {
        //AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        //final AlertDialog dialog = mBuilder.create();

        dialog.show();

        String urltemp = url + "tarjetas/crear_tarjeta";

        Map<String, String> params = new HashMap<String, String>();

        int Pos3 =  proyecto.getSelectedItemPosition();
        int Pos4 =  tipo_tarea.getSelectedItemPosition();

        if (TIPOUSUARIO.equals("SUPERUSER")){
            params.put("responsable",fragmento1.GetEncargadoID());
            params.put("tester", fragmento1.GetTesterID());
        }else{
            params.put("responsable",fragmento2.GetEncargadoID());
            params.put("tester", fragmento2.GetTesterID());
        }

        params.put("proyecto", Proyectos.get(Pos3-1).toString());
        params.put("tipo_tarea", Tareas.get(Pos4-1).toString());

        params.put("titulo", Titulo.getText().toString());
        params.put("fecha_entrega", Fechaentrega.getText().toString());
        params.put("link_ayuda", LinkAyuda.getText().toString());
        params.put("descripcion", Descripcion.getText().toString());


        params.put("link_evidencia", String.valueOf(LinkEvidencia.isChecked()) );
        params.put("horas_obligatorias", String.valueOf(tiempoObligatorio.isChecked()) );
        params.put("horas", Horas.getText().toString());

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urltemp, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respuesta = response.get("respuesta").toString();
                            if(respuesta.equals("si")){
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "ActivityVerTarjeta Creada.", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Error creando la tarjeta.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("Volley", "Invalid JSON Object.");
                            Toast.makeText(getApplicationContext(), "Error desconocido.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.e("Volley", error.toString());
                        Toast.makeText(getApplicationContext(), "Error en la conexion.", Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            /*
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                SharedPreferences SP = getSharedPreferences("TOKEN",MODE_PRIVATE);
                String tokenTemp = SP.getString("token","");
                headers.put("token", tokenTemp);
                return headers;
            }
        };

        requestQueue.add(arrReq);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String itemvalue = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(ActivityCrearTarjeta.this, "Selected: " + itemvalue, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

