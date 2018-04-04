package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
 * Created by Laura on 1/02/2018.
 */

public class Crear_Tester extends AppCompatActivity {

    int day, month, year;
    Calendar Date;
    String TIPOUSUARIO = "";
    String url;

    JSONArray JSONUsuarios;
    JSONArray JSONproyectos;

    ExpandableUserFragment fragmento1 = new ExpandableUserFragment();
    SpinnerUserFragment fragmento2 = new SpinnerUserFragment();

    MaterialSpinner  proyecto;

    List<String> proyectoItem = new ArrayList<>();
    List<String> Usuarios = new ArrayList<>();
    List<String> Proyectos = new ArrayList<>();
    ArrayAdapter<String>  proyectoAdapter;

    Button CrearTester;
    TextInputEditText  Fechaentrega, LinkAyuda, Descripcion ;
    TextInputLayout TILFechaentrega,TILLinkAyuda, TILDescripcion ;

    RequestQueue requestQueue;

    CheckBox  LinkEvidencia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tester);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Crear Tester");

        url = getString(R.string.URLWS);

        TILFechaentrega = (TextInputLayout) findViewById(R.id.fecha_e);
        Fechaentrega = (TextInputEditText) findViewById(R.id.EDTfecha);

        TILLinkAyuda = (TextInputLayout) findViewById(R.id.campo_link);
        LinkAyuda = (TextInputEditText) findViewById(R.id.link);

        TILDescripcion = (TextInputLayout) findViewById(R.id.campo_descripcion);
        Descripcion = (TextInputEditText) findViewById(R.id.descripcion);

        LinkEvidencia = (CheckBox) findViewById(R.id.evidencia);

        CrearTester = (Button) findViewById(R.id.BTNCrearTester);

        TIPOUSUARIO = getIntent().getStringExtra("TIPO");

        //Fecha
        Date = Calendar.getInstance();
        day = Date.get(Calendar.DAY_OF_MONTH);
        month = Date.get(Calendar.MONTH);
        year = Date.get(Calendar.YEAR);

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

        proyecto = (MaterialSpinner) findViewById(R.id.proyecto);
        proyectoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, proyectoItem);
        proyectoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proyecto.setAdapter(proyectoAdapter);

        CrearTester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VerificarCampos()) {
                    EnviarDatosWS();
                }
            }
        });
    }

    private void EnviarDatosWS() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView2 = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
        final AlertDialog dialog = mBuilder.create();

        dialog.show();

        String urltemp = url + "tarjetas/crear_tester";

        Map<String, String> params = new HashMap<String, String>();

        int Pos2 =  proyecto.getSelectedItemPosition();

        if (TIPOUSUARIO.equals("SUPERUSER")){
            params.put("responsable",fragmento1.GetEncargadoID());
        }else{
            params.put("responsable",fragmento2.GetEncargadoID());
        }

        params.put("proyecto", Proyectos.get(Pos2-1).toString());
        params.put("fecha_entrega", Fechaentrega.getText().toString());
        params.put("link_ayuda", LinkAyuda.getText().toString());
        params.put("descripcion", Descripcion.getText().toString());
        params.put("link_evidencia", String.valueOf(LinkEvidencia.isChecked()) );

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urltemp, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respuesta = response.get("respuesta").toString();
                            if(respuesta.equals("si")){
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Tester creado.", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Error creando el tester.", Toast.LENGTH_SHORT).show();
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

    private boolean VerificarCampos() {
       // int Pos1 =  responsable.getSelectedItemPosition();
        int Pos2 =  proyecto.getSelectedItemPosition();

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

        if (Pos2 == 0){
            proyecto.setError("Seleccione un proyecto.");
            return false;
        }else{
            proyecto.setError(null);
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

        return true;
    }

    private void CargarDatos() {
        String urltemp = url+"informacion/crear_tarjeta";

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView2 = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
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

                            CargarUsuarios();
                            CargarProyectos();

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
            transaction.replace(R.id.ContenedorCrearTester, fragmento1);
        }else{
            transaction.replace(R.id.ContenedorCrearTester, fragmento2);
        }
        transaction.commit();
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
