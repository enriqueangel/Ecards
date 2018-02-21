package com.example.enriq.ecards;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Corte extends AppCompatActivity {

    String url ;
    RequestQueue requestQueue;
    JSONArray CargarUsuarios;

    @Override
    protected void onStart() {

        String urltemp = url+"corte";

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final AlertDialog dialog = mBuilder.create();

        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, urltemp,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            CargarUsuarios = response.getJSONArray("horas");
                            cargarHoras();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "Error en la conexion", Toast.LENGTH_LONG).show();

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

    private void cargarHoras() throws JSONException {


        for (int i = 0; i < CargarUsuarios.length(); i++) {
            JSONObject row = CargarUsuarios.getJSONObject(i);

            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.HOUR_OF_DAY, 12);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            calendar.add(Calendar.HOUR, 1);  // numero de horas a añadir, o restar en caso de horas<0

            calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas


        }



    }

    ExpanListAdapterCorte listAdapter;
    ExpandableListView listView;
    List<String> listDataHeader;
    HashMap<String, List<UsuarioCorte>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corte);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Corte");

        url = getString(R.string.URLWS);

        listView = (ExpandableListView) findViewById(R.id.ramas);
        prepareListData();
        listAdapter = new ExpanListAdapterCorte(this, listDataHeader, listDataChild);
        listView.setAdapter(listAdapter);
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

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        listDataHeader.add("Sin problemas");
        listDataHeader.add("Con problemas");

        // Adding child data
        List<UsuarioCorte> top250 = new ArrayList<>();
        top250.add(new UsuarioCorte("Valentina Rojas", "18:30"));
        top250.add(new UsuarioCorte("Enrique Angel", "00:00"));
        top250.add(new UsuarioCorte("Ronal Gonzales", "03:00"));
        top250.add(new UsuarioCorte("Laura Gonzales", "12:45"));

        List<UsuarioCorte> nowShowing = new ArrayList<>();
        nowShowing.add(new UsuarioCorte("Laura Galenano", "10:00"));
        nowShowing.add(new UsuarioCorte("Daniela Ramirez", "08:00"));

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
    }


}
