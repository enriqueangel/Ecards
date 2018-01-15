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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    FloatingActionButton clickperf,clickDashboard;

    Date FechaServidor;



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
                            String dtStart = response.getString("fecha");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                FechaServidor = format.parse(dtStart);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
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
        clickDashboard = (FloatingActionButton) findViewById(R.id.exit);

        final VariablesGlobales globalVariable = (VariablesGlobales) getApplicationContext();


        clickDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Card.this, Dashboard.class);
                String areasTEmp = globalVariable.getAreas();
                intent.putExtra( "Areas", areasTEmp);
                startActivity(intent);
                finish();
            }
        });




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


    private void ImprimirTargetas() throws JSONException {

        for (int i = 0; i < TARJETAS.length(); i++) {
            JSONObject row = TARJETAS.getJSONObject(i);
            String DescripcionTEMP = row.getString("descripcion");

            JSONObject TipoTareaTEMP = row.getJSONObject("tipotarea");
            String TipoTEMP = TipoTareaTEMP.getString("nombre");

            String VersionTEMP = row.getString("version");

            String dtStart = row.getString("plazo");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date fechaFin ;
            long DiasRestantes = 100;
            try {
                fechaFin = format.parse(dtStart);
                DiasRestantes = calcularColor(fechaFin);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int ColorTArgeta ;

            if (DiasRestantes < 4){
                ColorTArgeta  = R.drawable.card_red;
            }else if(DiasRestantes < 2){
                ColorTArgeta  = R.drawable.card_green;
            }else{
                ColorTArgeta  = R.drawable.card_white;
            }


            listaTarjetas.add(new Fuente(DescripcionTEMP,"Frontend","32 Horas","10 Horas","1",ColorTArgeta,false));
        }

        contenedor = (RecyclerView) findViewById(R.id.contenedor);
        contenedor.setHasFixedSize(true);// no va a presentar variables en cuanto al tamaño
        RelativeLayout layout = new RelativeLayout(getApplicationContext());
        layout.setVerticalGravity(RelativeLayout.CENTER_VERTICAL);

        //INDICO CUAL TARJETA QUIERO MOSTRAR, PENDIENTE:PROGRAMAR LA ESCOGENCIA DE LA TARJETA
        contenedor.setAdapter(new Adaptador(listaTarjetas));


        contenedor.setLayoutManager(new LinearLayoutManager(this));

    };


    private long calcularColor(Date FechaFin){

        Date fechaInicio = FechaServidor;
        Date fechaLlegada = FechaFin;



        // tomamos la instancia del tipo de calendario
        Calendar calendarInicio = Calendar.getInstance();
        Calendar calendarFinal = Calendar.getInstance();

        // Configramos la fecha del calendatio, tomando los valores del date que
        // generamos en el parse
        calendarInicio.setTime(fechaInicio);
        calendarFinal.setTime(fechaLlegada);

        // obtenemos el valor de las fechas en milisegundos
        long milisegundos1 = calendarInicio.getTimeInMillis();
        long milisegundos2 = calendarFinal.getTimeInMillis();

        // tomamos la diferencia
        long diferenciaMilisegundos = milisegundos2 - milisegundos1;

        // Despues va a depender en que formato queremos  mostrar esa
        // diferencia, minutos, segundo horas, dias, etc, aca van algunos
        // ejemplos de conversion

        // calcular la diferencia en segundos
        long diffSegundos =  Math.abs (diferenciaMilisegundos / 1000);

        // calcular la diferencia en minutos
        long diffMinutos =  Math.abs (diferenciaMilisegundos / (60 * 1000));
        long restominutos = diffMinutos%60;

        // calcular la diferencia en horas
        long diffHoras =   (diferenciaMilisegundos / (60 * 60 * 1000));

        // calcular la diferencia en dias
        long diffdias = Math.abs ( diferenciaMilisegundos / (24 * 60 * 60 * 1000) );

        // devolvemos el resultado
        return diffdias;

    }




}


