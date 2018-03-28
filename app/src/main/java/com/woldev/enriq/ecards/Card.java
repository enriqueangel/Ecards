package com.woldev.enriq.ecards;

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

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Laura on 7/12/2017.
 */

/* contenedor es el id del recyclerview en el layout de cards*/

public class Card extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView contenedor;
    DrawerLayout drawer;
    ListView menulateral;

    AlertDialog dialog;

    int ContCardsBlancoint = 0;
    int ContCardsAmarilloint = 0;
    int ContCardsRojoint = 0;
    int ContCardsVerdeint = 0;
    int ContCardsAzulint = 0;


    String url;
    RequestQueue requestQueue;

    JSONArray TARJETAS;
    JSONArray Reuniones;
    JSONArray Testers;


    ArrayList<Fuente> listaTarjetas = new ArrayList<Fuente>();
    ArrayList<Fuente_Notificaciones> listanotificacion = new ArrayList<>();


    FloatingActionButton clickperf,clickDashboard;
    TextView ContCardsBlanco, ContCardsAmarillo,ContCardsRojo,ContCardsVerde,ContCardsAzul;

    Toolbar toolbar, cards;

    Date FechaServidor;

    @Override
    protected void onStart() {

        url = getString(R.string.URLWS);
        url = url+"tarjetas";
        dialog.show();

        requestQueue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap<String, String>();

        listaTarjetas.clear();

         ContCardsBlancoint = 0;
         ContCardsAmarilloint = 0;
         ContCardsRojoint = 0;
         ContCardsVerdeint = 0;
         ContCardsAzulint = 0;

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            TARJETAS = response.getJSONArray("tarjetas");
                            Reuniones = response.getJSONArray("reunion");
                            Testers = response.getJSONArray("tester");
                            String dtStart = response.getString("fecha");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                FechaServidor = format.parse(dtStart);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            ImprimirTesters();
                            ImprimirReuniones();
                            ImprimirTargetas();

                            ContCardsBlanco.setText( String.valueOf(ContCardsBlancoint));
                            ContCardsAmarillo.setText(String.valueOf(ContCardsAmarilloint));
                            ContCardsRojo.setText(String.valueOf(ContCardsRojoint));
                            ContCardsVerde.setText(String.valueOf(ContCardsVerdeint));
                            ContCardsAzul.setText(String.valueOf(ContCardsAzulint));

                            dialog.dismiss();



                        } catch (JSONException e) {
                            Log.e("Volley", "Invalid JSON Object.");
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Error cargando las tarjetas.", Toast.LENGTH_SHORT).show();
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

    private void ImprimirTesters() throws JSONException {
        for (int i = 0; i < Testers.length(); i++) {
            JSONObject row = Testers.getJSONObject(i);

        }

    }

    private void ImprimirReuniones() throws JSONException {
        for (int i = 0; i < Reuniones.length(); i++) {
            JSONObject row = Reuniones.getJSONObject(i);
            String DescripcionTEMP = row.getString("titulo");

            String TipoTEMP = row.getString("hora");

            String VersionTEMP = "1";

            String TiempoEsperado = row.getString("lugar");

            String dtStart2 = row.getString("fecha");

            String dtStart = "";

            // el que parsea
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            // el que formatea
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

            Date date = null;
            try {
                date = parseador.parse(dtStart2);
                dtStart = formateador.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            int ColorTArgeta ;

            String COLORTEMP = "azul";

            ColorTArgeta  = R.drawable.card_indigo;

            row.put("Color",COLORTEMP);

            ContCardsAzulint ++;

            listaTarjetas.add(new Fuente(DescripcionTEMP,TipoTEMP,dtStart,TiempoEsperado,VersionTEMP,ColorTArgeta,true,row));
        }

        contenedor = (RecyclerView) findViewById(R.id.contenedor);
        contenedor.setHasFixedSize(true);// no va a presentar variables en cuanto al tamaño
        RelativeLayout layout = new RelativeLayout(getApplicationContext());
        layout.setVerticalGravity(RelativeLayout.CENTER_VERTICAL);

        //INDICO CUAL TARJETA QUIERO MOSTRAR, PENDIENTE:PROGRAMAR LA ESCOGENCIA DE LA TARJETA
        contenedor.setAdapter(new Adaptador(listaTarjetas));

        contenedor.setLayoutManager(new LinearLayoutManager(this));


    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        menulateral =(ListView) findViewById(R.id.menulateral);
        toolbar = findViewById(R.id.toolbar);
        cards = findViewById(R.id.TARJETAS);

        ContCardsBlanco = (TextView) findViewById(R.id.card_blanco);
        ContCardsAmarillo = (TextView) findViewById(R.id.card_naranja);
        ContCardsRojo = (TextView) findViewById(R.id.card_rojo);
        ContCardsVerde = (TextView) findViewById(R.id.card_verde);
        ContCardsAzul = (TextView) findViewById(R.id.card_azul);

        listanotificacion.add(new Fuente_Notificaciones("hiada","nfsjdfns","fnjdsfn","fkdf"));
        listanotificacion.add(new Fuente_Notificaciones("hiada","nfsjdfns","fnjdsfn","fkdf"));
        listanotificacion.add(new Fuente_Notificaciones("hiada","nfsjdfns","fnjdsfn","fkdf"));
        listanotificacion.add(new Fuente_Notificaciones("hiada","nfsjdfns","fnjdsfn","fkdf"));
        listanotificacion.add(new Fuente_Notificaciones("hiada","nfsjdfns","fnjdsfn","fkdf"));
        listanotificacion.add(new Fuente_Notificaciones("hiada","nfsjdfns","fnjdsfn","fkdf"));
        listanotificacion.add(new Fuente_Notificaciones("hiada","nfsjdfns","fnjdsfn","fkdf"));
        listanotificacion.add(new Fuente_Notificaciones("hiada","nfsjdfns","fnjdsfn","fkdf"));
        listanotificacion.add(new Fuente_Notificaciones("hiada","nfsjdfns","fnjdsfn","fkdf"));
        listanotificacion.add(new Fuente_Notificaciones("hiada","nfsjdfns","fnjdsfn","fkdf"));
        listanotificacion.add(new Fuente_Notificaciones("hiada","nfsjdfns","fnjdsfn","fkdf"));

        //ArrayAdapter <String> adp = new ArrayAdapter<String>(this,R.layout.design_notificacion,opciones);
        //menulateral.setAdapter(adp);

        Adaptador_Notificaciones adapter = new Adaptador_Notificaciones(this, listanotificacion);
        menulateral.setAdapter(adapter);
        //listanotificacion.add(new Fuente_Notificaciones("hiada","nfsjdfns","fnjdsfn","fkdf"));
        //menulateral.setAdapter(new Adaptador_Notificaciones(listanotificacion));
        //menulateral.setLayoutManager(new LinearLayoutManager(this));


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tarjetas");

        clickperf = (FloatingActionButton) findViewById(R.id.perfil);
        clickDashboard = (FloatingActionButton) findViewById(R.id.exit);

        final VariablesGlobales globalVariable = (VariablesGlobales) getApplicationContext();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();

        //clickperf.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_persona));
        //clickDashboard.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_access_time_black_24dp));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
           case R.id.notification:

               if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                   drawer.closeDrawer(Gravity.RIGHT);
               }
               drawer.openDrawer(Gravity.RIGHT);
               return true;

               //menulateral.setNavigationItemSelectedListener(this);

            default:
               return super.onOptionsItemSelected(item);
        }
    }

    /*private void ImprimirNotificaciones() throws JSONException{
        for (int i = 0; i < TARJETASNOTI.length(); i++) {
            JSONObject row = TARJETASNOTI.getJSONObject(i);
            String TituloN = row.getString("titulo");

            String TipoTEMP;
            String VersionTEMP;

            String TiempoEsperado = row.getString("tiempo_estimado");
            String dtStart = row.getString("fecha_entrega");
    }*/



    private void ImprimirTargetas() throws JSONException {

        for (int i = 0; i < TARJETAS.length(); i++) {
            JSONObject row = TARJETAS.getJSONObject(i);
            String DescripcionTEMP = row.getString("titulo");

            String TipoTEMP;
            String VersionTEMP;

            String TiempoRealizado = row.getString("tiempo_trabajado");
            String dtStart2 = row.getString("fecha_entrega");
            String dtStart = "";
            Date FechaEntrega = null ;

            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");

            try {
                FechaEntrega = format2.parse(dtStart2);
                dtStart = parseador.format(FechaEntrega);
            } catch (ParseException e) {
                e.printStackTrace();
            }



            int ColorTArgeta ;

            String COLORTEMP = "";


            if (row.getString("tipo").equals("2")){
                TipoTEMP = "";
                VersionTEMP = "1";
                ColorTArgeta  = R.drawable.card_green;
                COLORTEMP = "verde";
                ContCardsVerdeint ++;
            }else{
                JSONObject TipoTareaTEMP = row.getJSONObject("tipotarea");
                TipoTEMP = TipoTareaTEMP.getString("tipo");
                VersionTEMP = row.getString("version");

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


                long DiasRestantes = 100;
                DiasRestantes = calcularColor(FechaEntrega);


                if (DiasRestantes <= 0){
                    ContCardsRojoint ++;
                    ColorTArgeta  = R.drawable.card_red;
                    COLORTEMP = "rojo";
                }else if(DiasRestantes <= 2){
                    ContCardsRojoint ++;
                    ColorTArgeta  = R.drawable.card_red;
                    COLORTEMP = "rojo";
                }else if(DiasRestantes <= 4){
                    ContCardsAmarilloint ++;
                    ColorTArgeta  = R.drawable.card_yellow;
                    COLORTEMP = "naranja";
                }else{
                    ContCardsBlancoint ++;
                    ColorTArgeta  = R.drawable.card_white;
                    COLORTEMP = "blanco";
                }
            }


            row.put("Color",COLORTEMP);

            listaTarjetas.add(new Fuente(DescripcionTEMP,TipoTEMP,dtStart,TiempoRealizado,VersionTEMP,ColorTArgeta,false,row));
        }

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
        long diffdias = diferenciaMilisegundos / (24 * 60 * 60 * 1000) ;

        // devolvemos el resultado
        return diffdias;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        return true;

    }
}


