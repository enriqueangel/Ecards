package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivityVerProyecto extends AppCompatActivity {

    String IDProyecto;
    RequestQueue requestQueue;
    String url ;
    TextView FechaEntrega,Estado,Lider,Cliente,Descripcion;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_proyecto);

        url = getString(R.string.URLWS);

        FechaEntrega = (TextView) findViewById(R.id.fecha);
        Estado = (TextView) findViewById(R.id.estado);
        Lider = (TextView) findViewById(R.id.lider);
        Cliente = (TextView) findViewById(R.id.cliente);
        Descripcion = (TextView) findViewById(R.id.descripcion);

        IDProyecto = getIntent().getExtras().getString("ID");

        CargarDatos();

        toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void CargarDatos() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView2 = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
        final AlertDialog dialogCargando = mBuilder.create();

        dialogCargando.show();
        String urltemp = url+"proyectos/informacion";
        requestQueue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap<String, String>();

        params.put("id", IDProyecto);

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urltemp, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject Respuesta = response.getJSONObject("proyecto");

                            toolbar.setTitle(Respuesta.getString("nombre"));
                            Estado.setText(Respuesta.getString("estado"));
                            Descripcion.setText(Respuesta.getString("descripcion"));
                            Cliente.setText(Respuesta.getString("cliente"));

                            JSONObject LiderTemp = Respuesta.getJSONObject("lider");
                            Lider.setText(LiderTemp.getString("nombres")+" "+LiderTemp.getString("apellidos"));

                            // el que parsea
                            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            // el que formatea
                            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

                            Date date = null;
                            try {
                                date = parseador.parse(Respuesta.getString("fecha_entrega"));
                                FechaEntrega.setText(formateador.format(date));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            dialogCargando.dismiss();

                        } catch (JSONException e) {
                            dialogCargando.dismiss();
                            Toast.makeText(getApplicationContext(), "Error cargando el proyecto", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
