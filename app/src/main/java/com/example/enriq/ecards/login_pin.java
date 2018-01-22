package com.example.enriq.ecards;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class login_pin extends AppCompatActivity {

    private TextInputLayout campoPin;

    TextView textViewbienv;
    Button buttonIngresarpin;
    EditText PIN;
    TextView TextOlvidePIN;

    String MetodoWS = "user/roles";

    RequestQueue requestQueue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pin);

        campoPin = (TextInputLayout) findViewById(R.id.campo_pin);
        textViewbienv = (TextView)findViewById(R.id.textViewbienv);
        buttonIngresarpin = (Button)findViewById(R.id.buttonIngresarpin);
        PIN = (EditText)findViewById(R.id.editTextIngresePIN );
        TextOlvidePIN = (TextView)findViewById(R.id.TextViewOlviPIN);

        final VariablesGlobales globalVariable = (VariablesGlobales) getApplicationContext();

        SharedPreferences SP = getSharedPreferences("PIN",MODE_PRIVATE);
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
                Intent intent = new Intent(login_pin.this, Recuperar_PIN.class);
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
                                        if (areas.length() < 2){
                                            Intent intent = new Intent(login_pin.this, Card.class);
                                            startActivity(intent);
                                            dialog.dismiss();
                                            finish();
                                        } else {
                                            Intent intent = new Intent(login_pin.this, Dashboard.class);
                                            intent.putExtra( "Areas", areas.toString());
                                            startActivity(intent);
                                            dialog.dismiss();
                                            finish();
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
}
