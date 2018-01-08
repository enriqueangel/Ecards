package com.example.enriq.ecards;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Laura on 7/12/2017.
 */

/* contenedor es el id del recyclerview en el layout de cards*/

public class Card extends AppCompatActivity {
    RecyclerView contenedor;

    String url;
    RequestQueue requestQueue;
    JSONArray TARJETAS;
    ArrayList<Fuente> listaTarjetas = new ArrayList<Fuente>();
    FloatingActionButton clickperf;

    @Override
    protected void onStart() {

        url = getString(R.string.URLWS);
        url = url+"tarjetas";

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final AlertDialog dialog = mBuilder.create();

        requestQueue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap<String, String>();


        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            TARJETAS = response.getJSONArray("tarjetas");
                            ImprimirTargetas();

                        } catch (JSONException e) {
                            Log.e("Volley", "Invalid JSON Object.");
                            Toast.makeText(getApplicationContext(), "Error desconocido.", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(view, "Error desconocido.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.e("Volley", error.toString());
                        Toast.makeText(getApplicationContext(), "Error en la conexion.", Toast.LENGTH_SHORT).show();
                        // Snackbar.make(view, "Error en la conexion.", Snackbar.LENGTH_SHORT).show();
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards);

        clickperf = (FloatingActionButton) findViewById(R.id.perfil);



//        lista.add(new Fuente("Creación de vista","Frontend","32 Horas","10 Horas","1",R.drawable.card_green,false));
//        lista.add(new Fuente("Creación de vista","Frontend","32 Horas","10 Horas","1",R.drawable.card_indigo,true));
//        lista.add(new Fuente("Creación de vista","Frontend","32 Horas","10 Horas","1",R.drawable.card_red,false));
//        lista.add(new Fuente("Creación de vista","Frontend","32 Horas","10 Horas","1",R.drawable.card_white,false));
//        lista.add(new Fuente("Creación de vista","Frontend","32 Horas","10 Horas","1",R.drawable.card_yellow,false));







        clickperf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                url = getString(R.string.URLWS);
                url = url+"user/datos_perfil";

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getApplicationContext());
                final AlertDialog dialog = mBuilder.create();

                requestQueue = Volley.newRequestQueue(getApplicationContext());

                Map<String, String> params = new HashMap<String, String>();


                JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    JSONObject Respuesta = response.getJSONObject("user");


                                    Intent intent = new Intent(Card.this, perfil.class);
                                    intent.putExtra( "DATOS", Respuesta.toString());
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                    //Toast.makeText(getApplicationContext(), "Error desconocido.", Toast.LENGTH_SHORT).show();
                                    Snackbar.make(v, "Error desconocido.", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.dismiss();
                                Log.e("Volley", error.toString());
                                //Toast.makeText(getApplicationContext(), "Error en la conexion.", Toast.LENGTH_SHORT).show();
                                 Snackbar.make(v, "Error en la conexion.", Snackbar.LENGTH_SHORT).show();
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
        });


    }


    private void ImprimirTargetas(){

        contenedor = (RecyclerView) findViewById(R.id.contenedor);
        contenedor.setHasFixedSize(true);// no va a presentar variables en cuanto al tamaño
        RelativeLayout layout = new RelativeLayout(getApplicationContext());
        layout.setVerticalGravity(RelativeLayout.CENTER_VERTICAL);

        //INDICO CUAL TARJETA QUIERO MOSTRAR, PENDIENTE:PROGRAMAR LA ESCOGENCIA DE LA TARJETA
        contenedor.setAdapter(new Adaptador(listaTarjetas));


        contenedor.setLayoutManager(new LinearLayoutManager(this));

    };




}


