package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class ActivityLoginPin extends AppCompatActivity {

    private TextInputLayout campoPin;

    TextView textViewbienv;
    Button buttonIngresarpin;
    EditText PIN;
    TextView TextOlvidePIN;

    String MetodoWS = "user/roles";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pin);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);

        campoPin = (TextInputLayout) findViewById(R.id.campo_pin);
        textViewbienv = (TextView)findViewById(R.id.textViewbienv);
        buttonIngresarpin = (Button)findViewById(R.id.buttonIngresarpin);
        PIN = (EditText)findViewById(R.id.editTextIngresePIN );
        TextOlvidePIN = (TextView)findViewById(R.id.TextViewOlviPIN);

        final VariablesGlobales globalVariable = (VariablesGlobales) getApplicationContext();

        SharedPreferences SP = getSharedPreferences("PIN",MODE_PRIVATE);
        SharedPreferences SP2 = getSharedPreferences("FireBase",MODE_PRIVATE);

        String TokenFBtemp  = SP2.getString("TokenFB","");
        String TokenFBnuevotemp  = SP2.getString("TokenFBnuevo","");

        if (!TokenFBtemp.equals(TokenFBnuevotemp)){
            ActualizarTokenFBenWS(TokenFBnuevotemp);
        }

        String CorreO = SP.getString("Correo","");
        textViewbienv.setText(CorreO);


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        requestQueue = Volley.newRequestQueue(this);

        TextOlvidePIN.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLoginPin.this, ActivityRecuperarPin.class);
                startActivity(intent);
            }
        });

        buttonIngresarpin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences SP = getSharedPreferences("PIN",MODE_PRIVATE);
                String pin = SP.getString("Pin", "");
                dialog.show();
                String url = getString(R.string.URLWS);
                url = url + MetodoWS;


                if(pin.equals( PIN.getText().toString())){
                    campoPin.setError(null);

                    JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, url,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray areas = response.getJSONArray("roles");
                                        PIN.setText("");
                                        globalVariable.setAreas(areas.toString());
                                        globalVariable.setCantRoles(areas.length());
                                        globalVariable.setUserID(response.getString("id"));
                                        if (areas.length() < 2){
                                            JSONObject area = areas.getJSONObject(0);
                                            String tipo = area.getString("tipo");
                                            Intent intent;
                                            switch (tipo){
                                                case "empleado":
                                                    globalVariable.setTipoUser("Empleado");
                                                    intent = new Intent(ActivityLoginPin.this, ActivityTarjetas.class);
                                                    startActivity(intent);
                                                    break;
                                                case "administrador":
                                                    globalVariable.setTipoUser("Administrador");
                                                    intent = new Intent(ActivityLoginPin.this, ActivityMenuAdmin.class);
                                                    startActivity(intent);
                                                    break;
                                                case "lider":
                                                    globalVariable.setTipoUser("Lider");
                                                    intent = new Intent(ActivityLoginPin.this, ActivityMenuLider.class);
                                                    startActivity(intent);
                                                    break;
                                                case "super":
                                                    globalVariable.setTipoUser("SuperU");
                                                    intent = new Intent(ActivityLoginPin.this, ActivityMenuSuperU.class);
                                                    startActivity(intent);
                                                    break;
                                                default:
                                                    break;
                                            }
                                            dialog.dismiss();
                                        } else {
                                            Intent intent = new Intent(ActivityLoginPin.this, ActivityDashboard.class);
                                            intent.putExtra( "Areas", areas.toString());
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    } catch (JSONException e) {
                                        Log.e("Volley", "Invalid JSON Object.");
                                        Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
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

                } else {
                    dialog.dismiss();
                    campoPin.setError("El PIN no es valido");
                }
            }
        });
    }

    private void ActualizarTokenFBenWS(final String NewToken) {
        SharedPreferences SP = getSharedPreferences("TOKEN",MODE_PRIVATE);
        final String  tokenWS = SP.getString("token","");

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        String urltemp = getString(R.string.URLWS)+"tokenfbput";

        Map<String, String> params = new HashMap<String, String>();
        params.put("TokenFB", NewToken);

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urltemp, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respuesta = response.get("respuesta").toString();
                            if(respuesta.equals("si")){
                                SharedPreferences SP2 = getSharedPreferences("FireBase",MODE_PRIVATE);
                                SharedPreferences.Editor editor2 = SP2.edit();
                                editor2.putString("TokenFB",NewToken);
                            } else {
                                Toast.makeText(getApplicationContext(), "Error enviando el token de notificaciones al WS", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error enviando el token de notificaciones al WS", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error enviando el token de notificaciones al WS", Toast.LENGTH_LONG).show();
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
                headers.put("token", tokenWS);
                return headers;
            }
        };

        requestQueue.add(arrReq);
    }
}
