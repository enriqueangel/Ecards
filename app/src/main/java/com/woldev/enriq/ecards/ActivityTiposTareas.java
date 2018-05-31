package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
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
import java.util.HashMap;
import java.util.Map;

public class ActivityTiposTareas extends AppCompatActivity implements View.OnClickListener {
    String url ;
    RequestQueue requestQueue;
    ArrayList<ElementoLista> tareas ;
    JSONArray DATOS;

    AlertDialog dialog;

    @Override
    protected void onStart() {
        TraerTareasWS();
        super.onStart();
    }

    private void TraerTareasWS() {
        dialog.show();
        tareas = new ArrayList<ElementoLista>();
        String urltemp = url+"tareas";
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, urltemp,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            CargarDATOS(response);
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    private void CargarDATOS(JSONObject response) throws JSONException  {
        DATOS = response.getJSONArray("tareas");

        for (int i = 0; i < DATOS.length(); i++) {
            JSONObject row = null;
            row = DATOS.getJSONObject(i);
            String NombreTEmp = row.getString("tipo");
            String BDidTEmp = row.getString("_id");
            tareas.add(new ElementoLista(NombreTEmp, BDidTEmp, row));
        }

        ListView list = (ListView) findViewById(R.id.lista);
        AdapterElementoListaButton adapter = new AdapterElementoListaButton(this, tareas);
        list.setAdapter(adapter);

        list.setEmptyView(findViewById(R.id.emptyElement));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipos_tareas);

        url = getString(R.string.URLWS);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Tipos Tareas");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();

        FloatingActionButton btnCrear = (FloatingActionButton) findViewById(R.id.agregar);
        btnCrear.setOnClickListener(this);
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
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.agregar:
                crearDialogAgregar();
//                i = new Intent(ActivityTiposTareas.this, ActivityCrearTipoTarea.class);
//                startActivity(i);
                break;
            default:
                break;
        }
    }

    private void crearDialogAgregar() {
        final AlertDialog.Builder agregarTarea = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        final View mView = this.getLayoutInflater().inflate(R.layout.dialog_crear_tarea, null);
        agregarTarea.setView(mView);
        agregarTarea.setTitle("Crear tipo tarea");

        final TextInputEditText Nombre = (TextInputEditText) mView.findViewById(R.id.nombre);
        final TextInputLayout CampoNombre = (TextInputLayout) mView.findViewById(R.id.CampoNombre);
        final RadioButton RadioButtonestudio = (RadioButton) mView.findViewById(R.id.estudio);
        final RadioButton RadioButtontrabajo = (RadioButton) mView.findViewById(R.id.trabajo);
        RadioButtonestudio.setChecked(true);

        agregarTarea.setPositiveButton("Reportar", null);

        agregarTarea.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = agregarTarea.create();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView2 = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
        final AlertDialog dialogCargando = mBuilder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean enviar = true;

                        if  (!MetodosGlobales.validarCampoVacio(Nombre.getText().toString())){
                            CampoNombre.setError("Debe ingresar el nombre");
                            enviar = false;
                        }else{
                            CampoNombre.setError(null);
                        }

                        if(enviar){
                            dialogCargando.show();

                            String urltemp = url+"tareas/crear";

                            requestQueue = Volley.newRequestQueue(getApplicationContext());

                            Map<String, String> params = new HashMap<String, String>();

                            params.put("tipo", Nombre.getText().toString());

                            String Clasificacion = "";

                            if(RadioButtonestudio.isChecked()){
                                Clasificacion = "estudio";
                            }else{
                                Clasificacion = "trabajo";
                            }

                            params.put("clasificacion", Clasificacion);

                            JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urltemp, new JSONObject(params),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                String Respuesta = response.getString("respuesta");
                                                if (Respuesta.equals("si")){
                                                    dialogCargando.dismiss();
                                                    dialog.dismiss();
                                                    TraerTareasWS();
                                                }else{
                                                    dialogCargando.dismiss();
                                                    Toast.makeText(getApplicationContext(), "Error creando el nuevo tipo de tarea", Toast.LENGTH_LONG).show();
                                                }

                                            } catch (JSONException e) {
                                                dialogCargando.dismiss();
                                                Toast.makeText(getApplicationContext(), "Error creando el nuevo tipo de tarea", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getApplicationContext(), "Error en la conexion", Toast.LENGTH_LONG).show();
                                        }
                                    }
                            ){


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
                    }
                });
            }
        });


        dialog.show();
    }
}
