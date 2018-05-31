package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class ActivityUsuariosNuevos extends AppCompatActivity {

    String url ;
    RequestQueue requestQueue;

    JSONArray DATOS;

    AlertDialog dialog;

    ArrayList<ElementoLista> usuarios;

    @Override
    protected void onStart() {
        dialog.show();

        usuarios = new ArrayList<ElementoLista>();

        String urltemp = url+"user/nuevos";
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, urltemp,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            CargarDATOS(response);
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

    private void CargarDATOS(JSONObject response) throws JSONException {

        DATOS = response.getJSONArray("users");

        for (int i = 0; i < DATOS.length(); i++) {
            JSONObject row = null;
            row = DATOS.getJSONObject(i);
            String NombreTEmp = row.getString("nombres");
            String ApellidosTEmp = row.getString("apellidos");
            String NombreMostrar = NombreTEmp+" "+ApellidosTEmp;
            String BDidTEmp = row.getString("_id");
            usuarios.add(new ElementoLista(NombreMostrar, BDidTEmp, row));
        }

        ListView list = (ListView) findViewById(R.id.lista);
        AdapterElementoLista adapter = new AdapterElementoLista(this, usuarios);
        list.setAdapter(adapter);

        list.setEmptyView(findViewById(R.id.emptyElement));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ElementoLista item = usuarios.get(i);
                crearDialogAsignar(item.getId());
            }
        });
    }

    private void crearDialogAsignar(final String IDUser) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final AlertDialog.Builder AsignarHoras = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        View mView = inflater.inflate(R.layout.dialog_asignar_horas, null);
        AsignarHoras.setView(mView);
        AsignarHoras.setTitle("Asignar horas");

        final TextInputEditText Horas = (TextInputEditText) mView.findViewById(R.id.horas);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView2 = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
        final AlertDialog dialogCargando = mBuilder.create();

        AsignarHoras.setPositiveButton("Asignar", null);

        AsignarHoras.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = AsignarHoras.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogCargando.show();
                        String urltemp = url+"user/asignar_horas";
                        requestQueue = Volley.newRequestQueue(getApplicationContext());

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", IDUser);
                        params.put("horas_contratadas", Horas.getText().toString());

                        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urltemp, new JSONObject(params),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String Respuesta = response.getString("respuesta");
                                            if (Respuesta.equals("si")){
                                                dialogCargando.dismiss();
                                                finish();
                                                Toast.makeText(getApplicationContext(), "Horas Asignadas", Toast.LENGTH_LONG).show();
                                            }else{
                                                dialogCargando.dismiss();
                                                Toast.makeText(getApplicationContext(), "Error asignando horas", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            dialogCargando.dismiss();
                                            Toast.makeText(getApplicationContext(), "Error asignando horas", Toast.LENGTH_LONG).show();
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
                });
            }
        });

        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_nuevos);

        url = getString(R.string.URLWS);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Usuarios Nuevos");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();
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
}
