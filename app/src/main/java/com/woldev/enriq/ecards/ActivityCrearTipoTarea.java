package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityCrearTipoTarea extends AppCompatActivity {

    Button btnModificarTarea;
    RadioButton rb1,rb2;
    String IDTarea,url;
    RequestQueue requestQueue;
    TextInputEditText Nombre;
    AlertDialog dialogCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tipo_tarea);


        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Crear Tipo Tarea");

        IDTarea = "";

        if(getIntent().getExtras()!=null){
            IDTarea =(getIntent().getExtras().getString("IDTAREA"));
        }

        btnModificarTarea = (Button) findViewById(R.id.BTNCreartarea);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        Nombre = (TextInputEditText) findViewById(R.id.EDTtarea);


        url = getString(R.string.URLWS);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView2 = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
        dialogCargar = mBuilder.create();
        dialogCargar.show();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Map<String, String> params = new HashMap<String, String>();


        params.put("id", IDTarea);

        String URLtemp  = url+"tareas/informacion";
        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, URLtemp, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject Respuesta = response.getJSONObject("tarea");
                            Nombre.setText(Respuesta.getString("tipo"));
                            if(Respuesta.getString("clasificacion_tarea").equals("estudio")){
                                rb1.setChecked(true);
                            }else{
                                rb2.setChecked(true);
                            }
                            dialogCargar.dismiss();
                        } catch (JSONException e) {
                            dialogCargar.dismiss();
                            Log.e("Volley", "Invalid JSON Object.");
                            Toast.makeText(getApplicationContext(), "Error desconocido.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialogCargar.dismiss();
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

        btnModificarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModificarTarea();
            }
        });

    }

    private void ModificarTarea() {

        dialogCargar.show();

        String urltemp = url+"tareas/editar";

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Map<String, String> params = new HashMap<String, String>();

        params.put("tipo", Nombre.getText().toString());
        params.put("id", IDTarea);
        String Clasificacion = "";

        if(rb1.isChecked()){
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
                                dialogCargar.dismiss();
                                finish();
                                Toast.makeText(getApplicationContext(), "Datos modificados", Toast.LENGTH_LONG).show();

                            }else{
                                dialogCargar.dismiss();
                                Toast.makeText(getApplicationContext(), "Error modificando el tipo de tarea", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            dialogCargar.dismiss();
                            Toast.makeText(getApplicationContext(), "Error modificando el tipo de tarea", Toast.LENGTH_LONG).show();
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
