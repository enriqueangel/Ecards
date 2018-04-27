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
import android.widget.AdapterView;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HistorialCorteActivity extends AppCompatActivity {

    String url;
    RequestQueue requestQueue;
    AlertDialog dialog;
    ListView lista1;

    ArrayList IDs = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_corte);

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


        lista1.setOnItemClickListener(new ItemList());


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
                            Toast.makeText(HistorialCorteActivity.this, "Error cargando los cortes.", Toast.LENGTH_SHORT).show();
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

            String dtStart = "";
            Date FechaEntrega = null ;

            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");

            try {
                FechaEntrega = format2.parse(row.getString("fecha_inicio"));
                dtStart = parseador.format(FechaEntrega);
                FechaEntrega = format2.parse(row.getString("fecha_finalizacion"));
                dtStart = dtStart+"/"+parseador.format(FechaEntrega);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            items.add(dtStart);
            IDs.add(row.getString("fecha_inicio"));
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


    class ItemList implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String IDtemp = (String) IDs.get(position);

            Intent i = getIntent();
            // Le metemos el resultado que queremos mandar a la
            // actividad principal.
            i.putExtra("IDCorte", IDtemp);
            // Establecemos el resultado, y volvemos a la actividad
            // principal. La variable que introducimos en primer lugar
            // "RESULT_OK" es de la propia actividad, no tenemos que
            // declararla nosotros.
            setResult(RESULT_OK, i);

            // Finalizamos la Activity para volver a la anterior
            finish();


        }
    }





}
