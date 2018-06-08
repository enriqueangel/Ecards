package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Laura on 7/12/2017.
 */

/* contenedor es el id del recyclerview en el layout de activity_tarjetas*/

public class ActivityTarjetas extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ListView menulateral;

    int ContCardsBlancoint = 0;
    int ContCardsAmarilloint = 0;
    int ContCardsRojoint = 0;
    int ContCardsVerdeint = 0;
    int ContCardsAzulint = 0;
    int ContCardsMoradoint = 0;

    String url;
    RequestQueue requestQueue;
    FragmentListaTarjetas FragmentListaTarjetas;

    ArrayList<Notificacion> listanotificacion = new ArrayList<>();

    FloatingActionButton clickperf,clickDashboard;
    TextView ContCardsBlanco, ContCardsAmarillo,ContCardsRojo,ContCardsVerde,ContCardsAzul,ContCardsMorado;
    Toolbar toolbar, cards;
    Date FechaServidor;

    JSONArray notificaciones=null;

    @Override
    protected void onStart() {
        String urlTemp=getString(R.string.URLWS)+"notificaciones";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, urlTemp,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                                notificaciones = response.getJSONArray("notificaciones");
                                ImprimirNotificaciones();

                        } catch (JSONException e) {
                            Log.e("Volley", "Invalid JSON Object.");
                            Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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

    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        menulateral =(ListView) findViewById(R.id.menulateral);
        toolbar = findViewById(R.id.toolbar);
        cards = findViewById(R.id.TARJETAS);

        ContCardsBlanco = (TextView) findViewById(R.id.card_blanco);
        ContCardsAmarillo = (TextView) findViewById(R.id.card_naranja);
        ContCardsRojo = (TextView) findViewById(R.id.card_rojo);
        ContCardsVerde = (TextView) findViewById(R.id.card_verde);
        ContCardsAzul = (TextView) findViewById(R.id.card_azul);
        ContCardsMorado = (TextView) findViewById(R.id.card_purple);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tarjetas");

        clickperf = (FloatingActionButton) findViewById(R.id.perfil);
        clickDashboard = (FloatingActionButton) findViewById(R.id.exit);

        final VariablesGlobales globalVariable = (VariablesGlobales) getApplicationContext();

        FragmentListaTarjetas = new FragmentListaTarjetas();

        Bundle args2 = new Bundle();
        String IDUsuarioTemp  = globalVariable.getUserID();
        args2.putString("ID", IDUsuarioTemp);

        FragmentListaTarjetas.setArguments(args2);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.contenedor, FragmentListaTarjetas);

        transaction.commit();

        if (globalVariable.getCantRoles() == 1){
            clickDashboard.setVisibility(View.GONE);
        }

        clickDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTarjetas.this, ActivityDashboard.class);
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

                                    Intent intent = new Intent(ActivityTarjetas.this, ActivityPerfil.class);
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

    public void PintarCantTarjetas() {

        ContCardsBlancoint = FragmentListaTarjetas.ContCardsBlancoint;
        ContCardsAmarilloint = FragmentListaTarjetas.ContCardsAmarilloint;
        ContCardsRojoint = FragmentListaTarjetas.ContCardsRojoint;
        ContCardsVerdeint = FragmentListaTarjetas.ContCardsVerdeint;
        ContCardsAzulint = FragmentListaTarjetas.ContCardsAzulint;
        ContCardsMoradoint = FragmentListaTarjetas.ContCardsMoradoint;

        ContCardsBlanco.setText( String.valueOf(ContCardsBlancoint));
        ContCardsAmarillo.setText(String.valueOf(ContCardsAmarilloint));
        ContCardsRojo.setText(String.valueOf(ContCardsRojoint));
        ContCardsVerde.setText(String.valueOf(ContCardsVerdeint));
        ContCardsAzul.setText(String.valueOf(ContCardsAzulint));
        ContCardsMorado.setText(String.valueOf(ContCardsMoradoint));
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

    private void ImprimirNotificaciones() throws JSONException{
        for (int i = 0; i < notificaciones.length(); i++) {
            JSONObject row = notificaciones.getJSONObject(i);


            String FechaN = row.getString("fecha");
            String DescripcionN = row.getString("descripcion");
            String dtStart = "";
            String HoraTEmp= "";
            Date FechaEntrega = null ;

            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat parseador2 = new SimpleDateFormat("HH:mm");

            try {
                FechaEntrega = format2.parse(FechaN);
                dtStart = parseador.format(FechaEntrega);
                HoraTEmp = parseador2.format(FechaEntrega);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            listanotificacion.add(new Notificacion(DescripcionN,"",dtStart,HoraTEmp));


            //ArrayAdapter <String> adp = new ArrayAdapter<String>(this,R.layout.design_notificacion,opciones);
            //menulateral.setAdapter(adp);

            AdapterNotificaciones adapter = new AdapterNotificaciones(this, listanotificacion);
            menulateral.setAdapter(adapter);

    }}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        return true;
    }
}


