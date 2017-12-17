package com.example.enriq.ecards;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Recuperar_PIN extends AppCompatActivity {

    private TextInputLayout campoContrasena;

    TextView Correo;
    Button BTRecuperarPIN;
    String CorreO;
    EditText Contra;
    String MetodoWS = "recuperar_pin";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar__pin);


        BTRecuperarPIN = (Button)findViewById( R.id.BotonRecuperar);
        Contra = (EditText) findViewById( R.id.EDTContra);
        campoContrasena = (TextInputLayout) findViewById( R.id.TextImputContra);

        //recupera el correo
        SharedPreferences SP = getSharedPreferences("PIN",MODE_PRIVATE);
        CorreO = SP.getString("Correo","");
        Correo = (TextView) findViewById(R.id.TvCorreo);
        Correo.setText(CorreO);

        requestQueue = Volley.newRequestQueue(this);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();



        BTRecuperarPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean confirmContrasena = validarContraseña(Contra);

                if (confirmContrasena){

                    dialog.show();
                    final String correo_inp = CorreO;
                    final String password_inp = Contra.getText().toString();

                    String url = getString(R.string.URLWS);
                    url = url+MetodoWS;



                    Map<String, String> params = new HashMap<String, String>();
                    params.put("correo", correo_inp);
                    params.put("password", password_inp);

                    JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String respuesta = response.get("respuesta").toString();
                                        if(respuesta.equals("si")){
                                            dialog.dismiss();
                                            Intent intent = new Intent(Recuperar_PIN.this, Crear_Pin.class);
                                            intent.putExtra( "Correo", CorreO );
                                            startActivity(intent);

                                            finish();
                                        } else {
                                            dialog.dismiss();
                                            Toast MensajeBienvenido = Toast.makeText(Recuperar_PIN.this, "Contraseña incorrecta.", Toast.LENGTH_SHORT);
                                            MensajeBienvenido.show();
                                            //Snackbar.make(view, "Usuario no existe.", Snackbar.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        Log.e("Volley", "Invalid JSON Object.");
                                        Toast MensajeBienvenido = Toast.makeText(Recuperar_PIN.this, "Error desconocido.", Toast.LENGTH_SHORT);
                                        MensajeBienvenido.show();
                                        //Snackbar.make(view, "Error desconocido.", Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    dialog.dismiss();
                                    Log.e("Volley", error.toString());
                                    Toast MensajeBienvenido = Toast.makeText(Recuperar_PIN.this, "Error en la conexion", Toast.LENGTH_SHORT);
                                    MensajeBienvenido.show();
                                    //Snackbar.make(view, "Error en la conexion.", Snackbar.LENGTH_SHORT).show();
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


                };


            }
        });

    }

    public boolean validarContraseña(EditText contasena){
        if (TextUtils.isEmpty(contasena.getText())){
            campoContrasena.setError("Ingrese contraseña");
            return false;
        } else {
            campoContrasena.setError(null);
            return true;
        }
    }



}
