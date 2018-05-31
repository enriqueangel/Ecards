package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class ActivityRamas extends AppCompatActivity  {

    ArrayList<ElementoLista> ramas;
    ListView list;

    String url ;
    RequestQueue requestQueue;

    JSONArray DATOS;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramas);

        url = getString(R.string.URLWS);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Ramas");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        list = (ListView) findViewById(R.id.lista);

        //FloatingActionButton btnCrear = (FloatingActionButton) findViewById(R.id.agregar);
        //btnCrear.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        dialog.show();
        ramas = new ArrayList<ElementoLista>();

        String urltemp = url+"ramas";

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

    private void CargarDATOS(JSONObject response) throws JSONException {
        DATOS = response.getJSONArray("ramas");

        for (int i = 0; i < DATOS.length(); i++) {
            JSONObject row = null;
            row = DATOS.getJSONObject(i);
            String NombreTEmp = row.getString("nombre");
            String BDidTEmp = row.getString("_id");
            ramas.add(new ElementoLista(NombreTEmp, BDidTEmp, row));
        }

        AdapterElementoLista adapter = new AdapterElementoLista(this, ramas);
        list.setAdapter(adapter);

        list.setEmptyView(findViewById(R.id.emptyElement));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ElementoLista item = ramas.get(i);
                Intent b =  new Intent(ActivityRamas.this, ActivityRama.class);
                b.putExtra("IDRAMA", item.getId());
                startActivity(b);
            }
        });
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

/*
   @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.agregar:
                i = new Intent(ActivityRamas.this, ActivityCrearProyecto.class);
                startActivity(i);
                break;
            default:
                break;
        }

    }*/




}
