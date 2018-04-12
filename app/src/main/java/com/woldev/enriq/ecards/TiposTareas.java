package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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

public class TiposTareas extends AppCompatActivity implements View.OnClickListener {

    String url ;
    RequestQueue requestQueue;
    ArrayList<ElementoLista> tareas ;
    JSONArray DATOS;

    AlertDialog dialog;

    @Override
    protected void onStart() {
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
        super.onStart();
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
        ElementoListButtonAdapter adapter = new ElementoListButtonAdapter(this, tareas);
        list.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        switch (view.getId()){
            case R.id.agregar:
                crearDialogAgregar();
                break;
        }
    }

    private void crearDialogAgregar() {
        final AlertDialog.Builder agregarTarea = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        final View mView = this.getLayoutInflater().inflate(R.layout.dialog_crear_tarea, null);
        agregarTarea.setView(mView);
        agregarTarea.setTitle("Crear tipo tarea");

        agregarTarea.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Tipo tarea creado", Toast.LENGTH_LONG).show();
            }
        });

        agregarTarea.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = agregarTarea.create();
        dialog.show();
    }
}
