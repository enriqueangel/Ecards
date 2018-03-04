package com.woldev.enriq.ecards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

/**
 * Created by enriq on 18/12/2017.
 */

public class RecuperarContrasena extends AppCompatActivity {

    private TextInputLayout campoContrasena;
    private TextInputLayout campoConfirmar;
    private TextInputLayout campoCodigo;

    EditText codigo, contrasena, confirmar;
    RequestQueue requestQueue;
    Button btnCambiar;
    String MetodoWS = "recuperar_contrasena";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);

        campoContrasena = (TextInputLayout) findViewById(R.id.campo_contrasena);
        campoConfirmar = (TextInputLayout) findViewById(R.id.campo_confirmar);
        campoCodigo = (TextInputLayout) findViewById(R.id.campo_codigo);
        contrasena = (EditText) findViewById(R.id.contrasena);
        confirmar = (EditText) findViewById(R.id.confirmar);
        codigo = (EditText) findViewById(R.id.codigo);
        btnCambiar = (Button) findViewById(R.id.BotonRecuperar);

        requestQueue = Volley.newRequestQueue(this);

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                boolean confirmCodigo = validarCodigo(codigo.getText().toString());
                boolean confirmContrasena = validarContraseña(contrasena);
                boolean confirmConfirm = validarConfirm(confirmar, contrasena, confirmContrasena);

                if (confirmCodigo && confirmConfirm && confirmContrasena){
                    final String password_inp = contrasena.getText().toString();
                    String correo = getIntent().getExtras().getString("Correo");

                    String url = getString(R.string.URLWS);
                    url = url + MetodoWS;

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("correo", correo);
                    params.put("password", password_inp);

                    JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String respuesta = response.get("respuesta").toString();
                                        if(respuesta.equals("si")){
                                            Toast.makeText(getApplicationContext(),"Contraseña cambiada", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(RecuperarContrasena.this, MainActivity.class);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(),"Error al hacer el cambio", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        Log.e("Volley", "Invalid JSON Object.");
                                        Toast.makeText(getApplicationContext(),"Error desconocido", Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Volley", error.toString());
                                    Toast.makeText(getApplicationContext(),"Error en la conexion", Toast.LENGTH_LONG).show();
                                }
                            }
                    );

                    requestQueue.add(arrReq);
                }
            }
        });
    }

    public boolean validarCodigo(String codigo){
        if (!codigo.equals(getIntent().getExtras().getString("Codigo"))){
            campoCodigo.setError("El codigo es erroneo");
            return false;
        } else {
            campoCodigo.setError(null);
            return true;
        }
    }

    public boolean validarContraseña(EditText contrasena){
        if (TextUtils.isEmpty(contrasena.getText())){
            campoContrasena.setError("Ingrese contraseña");
            return false;
        } else {
            campoContrasena.setError(null);
            return true;
        }
    }

    public boolean validarConfirm(EditText confirmar, EditText contrasena, boolean valor){
        if (TextUtils.isEmpty(confirmar.getText())){
            campoConfirmar.setError("Ingrese confirmacion de contraseña");
            return false;
        } else {
            campoConfirmar.setError(null);
            if (valor){
                if (!confirmar.getText().toString().equals(contrasena.getText().toString())){
                    campoContrasena.setError("Las contraseñas deben ser iguales");
                    campoConfirmar.setError("Las contraseñas deben ser iguales");
                    return false;
                } else {
                    campoContrasena.setError(null);
                    campoConfirmar.setError(null);
                    return true;
                }
            }
            return false;
        }
    }
}
