package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
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

    String url ,TipoUsuario ;
    RequestQueue requestQueue;
    JSONArray CargarUsuarios;
    AlertDialog dialog;

    private static final int CHILD_REQUEST = 696;



    @Override
    protected void onStart() {

        String urltemp = url+"corte";

        dialog.show();

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

                        dialog.dismiss();

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

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        listDataHeader.add("Sin problemas");
        listDataHeader.add("Con problemas");

        // Adding child data
        List<UsuarioCorte> SinProblemas = new ArrayList<>();
        List<UsuarioCorte> ConProblemas = new ArrayList<>();

        for (int i = 0; i < CargarUsuarios.length(); i++) {
            JSONObject row = CargarUsuarios.getJSONObject(i);

            Calendar calendar = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();

            JSONObject UserTemp = row.getJSONObject("usuario");
            String HorasContratas = UserTemp.getString("horas_contratadas");
            String[] separated0 = HorasContratas.split(":");
            int horasContratadasTemp = Integer.parseInt(separated0[0]);
            int MinutosContratasTemp = Integer.parseInt(separated0[1]);

            String ReunionTimeTemp = row.getString("horas_reunion");
            String[] separated = ReunionTimeTemp.split(":");
            int horasReunionTemp = Integer.parseInt(separated[0]);
            int MinutosReunionTemp = Integer.parseInt(separated[1]);


            String EstudioTimeTemp = row.getString("horas_estudio");
            String[] separated2 = EstudioTimeTemp.split(":");
            int horasEstudioTemp = Integer.parseInt(separated2[0]);
            int MinutosEstudioTemp = Integer.parseInt(separated2[1]);

            String TrabajoTimeTemp = row.getString("horas_trabajadas");
            String[] separated3 = TrabajoTimeTemp.split(":");
            int horasTrabajoTemp = Integer.parseInt(separated3[0]);
            int MinutosTrabajoTemp = Integer.parseInt(separated3[1]);


            calendar.set(Calendar.HOUR_OF_DAY, horasContratadasTemp);
            calendar.set(Calendar.MINUTE, MinutosContratasTemp);
            calendar.set(Calendar.SECOND, 0);

            calendar2.set(Calendar.HOUR_OF_DAY, 0);
            calendar2.set(Calendar.MINUTE, 0);
            calendar2.set(Calendar.SECOND, 0);

            calendar2.add(Calendar.HOUR, horasReunionTemp);
            calendar2.add(Calendar.HOUR, horasEstudioTemp);
            calendar2.add(Calendar.HOUR, horasTrabajoTemp);

            calendar2.add(Calendar.MINUTE, MinutosReunionTemp);
            calendar2.add(Calendar.MINUTE, MinutosEstudioTemp);
            calendar2.add(Calendar.MINUTE, MinutosTrabajoTemp);

            Date Fecha1 = calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas
            Date Fecha2 = calendar2.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas


            long diff = Fecha1.getTime() - Fecha2.getTime();

            Boolean IsNegativo = false;

            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;

            if (diffHours < 0){
                IsNegativo = true;
                diffHours = diffHours*-1;
            }

            if (diffMinutes > 0){
                diffHours = diffHours-1;
                diffMinutes = 60-diffMinutes;
            }else{
                diffMinutes = diffMinutes*-1;
            }

            String HorasFinalTemp = String.valueOf(diffHours) ;
            String MinutosFinalTemp = String.valueOf(diffMinutes) ;

            if (diffHours < 10){
                HorasFinalTemp = "0"+String.valueOf(diffHours);
            }

            if (diffMinutes < 10){
                MinutosFinalTemp = "0"+String.valueOf(diffMinutes);
            }

            String TiempoTemp = HorasFinalTemp+":"+MinutosFinalTemp;

            if (IsNegativo){
                TiempoTemp = "-"+TiempoTemp;
            }

            String NombreMostrarTemp = UserTemp.getString("nombres") +" "+ UserTemp.getString("apellidos");

            if (IsNegativo || TiempoTemp.equals("00:00")){
                SinProblemas.add(new UsuarioCorte(NombreMostrarTemp, TiempoTemp));
            }else{
                ConProblemas.add(new UsuarioCorte(NombreMostrarTemp, TiempoTemp));
            }

        }

        listDataChild.put(listDataHeader.get(0), SinProblemas); // Header, Child data
        listDataChild.put(listDataHeader.get(1), ConProblemas);

        listAdapter = new ExpanListAdapterCorte(this, listDataHeader, listDataChild);
        listView.setAdapter(listAdapter);


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

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView2 = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
        dialog = mBuilder.create();


        TipoUsuario = getIntent().getStringExtra("TIPO");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.historial, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHILD_REQUEST){

            switch (resultCode) {
                case RESULT_OK:

                    String urltemp = url+"corte/info";

                    dialog.show();

                    Map<String, String> params = new HashMap<String, String>();

                    String resultado = data.getExtras().getString("IDCorte");

                    params.put("id", resultado);

                    JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urltemp, new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {


                                        CargarUsuarios = response.getJSONArray("horas");
                                        cargarHoras();
                                        dialog.dismiss();


                                    } catch (JSONException e) {
                                        Log.e("Volley", "Invalid JSON Object.");
                                        dialog.dismiss();
                                        Toast.makeText(Corte.this, "Error cargando el corte", Toast.LENGTH_SHORT).show();
                                        //Snackbar.make(view, "Error desconocido.", Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    dialog.dismiss();
                                    Log.e("Volley", error.toString());
                                    Toast.makeText(Corte.this, "Error en la conexion.", Toast.LENGTH_SHORT).show();
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
                            SharedPreferences SP = Corte.this.getSharedPreferences("TOKEN",MODE_PRIVATE);
                            String tokenTemp = SP.getString("token","");
                            headers.put("token", tokenTemp);
                            return headers;
                        }
                    };

                    requestQueue.add(arrReq);

                    break;

                case RESULT_CANCELED:
                    // Cancelación o cualquier situación de error
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent i;
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.historial:
                i = new Intent(Corte.this, HistorialCorteActivity.class);
                startActivityForResult(i, CHILD_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
