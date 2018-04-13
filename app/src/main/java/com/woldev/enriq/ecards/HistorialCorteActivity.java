package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistorialCorteActivity extends AppCompatActivity {

    String url;
    RequestQueue requestQueue;
    AlertDialog dialog;
    ListView lista1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial_corte);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Historial Corte");


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();


        url = getString(R.string.URLWS);
        url = url+"corte/historial";
        dialog.show();

        requestQueue = Volley.newRequestQueue(this);

        lista1 = (ListView) findViewById(R.id.lista1);



        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray respt = response.getJSONArray("horas");
                            CargarLista(respt);

                            dialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                            Toast.makeText(HistorialCorteActivity.this, "Error cargando las tarjetas.", Toast.LENGTH_SHORT).show();
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

    private void CargarLista(JSONArray Lista) throws JSONException {

        ArrayList items = new ArrayList();

        for (int i = 0; i < Lista.length(); i++) {
            JSONObject row = Lista.getJSONObject(i);
            String Fecha = row.getString("fecha_inicio")+row.getString("fecha_finalizacion");
            items.add(Fecha);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        lista1.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent i;
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
