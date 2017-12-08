package com.example.enriq.ecards;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Nuevos
import android.text.method.ScrollingMovementMethod;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout campoCorreo;
    private TextInputLayout campoContrasena;
    private Typeface Roboto;

    EditText correo, contrasena;
    Button btn_singin, btn_singup;
    RequestQueue requestQueue;

    boolean email = false, password = false;
    String baseUrl = "https://webserver-enriqeangel.c9users.io/";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String fuente = "fuentes/Roboto.ttf";
        this.Roboto = Typeface.createFromAsset(getAssets(), fuente);

        // Inicializo campos del layout
        correo = (EditText) findViewById(R.id.correo);
        contrasena = (EditText) findViewById(R.id.contrasena);
        btn_singin = (Button) findViewById(R.id.btn_singin);
        btn_singup = (Button) findViewById(R.id.btn_singup);
        campoCorreo = (TextInputLayout) findViewById(R.id.campo_correo);
        campoContrasena = (TextInputLayout) findViewById(R.id.campo_contrasena);

        requestQueue = Volley.newRequestQueue(this);

        campoCorreo.setTypeface(Roboto);
        campoContrasena.setTypeface(Roboto);
        btn_singin.setTypeface(Roboto);
        btn_singup.setTypeface(Roboto);

        // Cuando se le da clik al boton genera evento
        btn_singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtiene valor del campo de correo y valida si es formato email
                if(Patterns.EMAIL_ADDRESS.matcher(correo.getText().toString()).matches() == false) {
                    email = false;
                    campoCorreo.setError("Correo erroneo");
                    // Toast.makeText(getApplicationContext(), "Correo erroneo", Toast.LENGTH_SHORT).show();
                } else {
                    email = true;
                    campoCorreo.setError(null);
                }

                // Valida que la contraseña no este vacia
                if(TextUtils.isEmpty(contrasena.getText())) {
                    campoContrasena.setError("Ingrese contraseña");
                    password = false;
                } else {
                    campoContrasena.setError(null);
                    password = true;
                    // Toast.makeText(getApplicationContext(), "Ingrese contraseña", Toast.LENGTH_SHORT).show();
                }

                if(email && password){
                    final String correo_inp = correo.getText().toString();
                    final String password_inp = contrasena.getText().toString();

                    // ---------- Metodo Post ----------
                    url = baseUrl + "login";

                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("correo", correo_inp);
                    params.put("password", password_inp);

                    JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String respuesta = response.get("respuesta").toString();
                                        if(respuesta.equals("si")) {
                                            Toast.makeText(getApplicationContext(), "Usuario existe", Toast.LENGTH_SHORT).show();
                                            Intent nuevavista = new Intent(MainActivity.this, Cards.class);
                                            startActivity(nuevavista);
                                        } else
                                            Toast.makeText(getApplicationContext(), "Usuario no existe", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        Log.e("Volley", "Invalid JSON Object.");
                                        Toast.makeText(getApplicationContext(), "Otro error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Volley", error.toString());
                                    Toast.makeText(getApplicationContext(), "Error en la conexion", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                    // ---------- Metodo Get ----------
                    // url = baseUrl + "login?correo=" + correo_inp + "&password=" + password_inp;

                    /*JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, url,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String respuesta = response.get("respuesta").toString();
                                        // Toast.makeText(getApplicationContext(), respuesta, Toast.LENGTH_SHORT).show();
                                        if(respuesta.equals("si"))
                                            Toast.makeText(getApplicationContext(), "Usuario existe", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(getApplicationContext(), "Usuario no existe", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        Log.e("Volley", "Invalid JSON Object.");
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Volley", error.toString());
                                    Toast.makeText(getApplicationContext(), "Error en la conexion", Toast.LENGTH_SHORT).show();
                                }
                            }

                    );*/

                    requestQueue.add(arrReq);

                }
            }
        });
    }
}
