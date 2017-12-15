package com.example.enriq.ecards;

/**
 * Created by enriq on 14/12/2017.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.regex.Pattern;

public class SignInFragment extends Fragment {

    private TextInputLayout campoCorreo;
    private TextInputLayout campoContrasena;

    EditText correo, contrasena;
    Button btnSignIn, btnSignUp;
    RequestQueue requestQueue;
    Activity Actividad;
    String baseUrl = "https://webserver-enriqeangel.c9users.io/";
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        // Inicializo campos del layout
        correo = (EditText) view.findViewById(R.id.correo);
        contrasena = (EditText) view.findViewById(R.id.contrasena);
        btnSignIn = (Button) view.findViewById(R.id.btnSignIn);
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        campoCorreo = (TextInputLayout) view.findViewById(R.id.campo_correo);
        campoContrasena = (TextInputLayout) view.findViewById(R.id.campo_contrasena);

        requestQueue = Volley.newRequestQueue(getActivity());
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                boolean confirmCorreo = validarCorreo(correo.getText().toString());
                boolean confirmContrasena = validarContraseña(contrasena);
                if (confirmCorreo && confirmContrasena){
                    dialog.show();
                    final String correo_inp = correo.getText().toString();
                    final String password_inp = contrasena.getText().toString();

                    url = baseUrl + "login";

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
                                            String token = response.get("token").toString();
                                            Actividad = getActivity();
                                            Toast MensajeBienvenido = Toast.makeText(Actividad, "Bienvenido ", Toast.LENGTH_SHORT);
                                            MensajeBienvenido.show();

                                            SharedPreferences SP = Actividad.getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = SP.edit();
                                            editor.putString("token",token);
                                            editor.apply();

                                            Intent intent = new Intent(getActivity(), Crear_Pin.class);
                                            intent.putExtra( "Correo", correo.getText().toString()  );
                                            startActivity(intent);

                                            Actividad.finish();

                                            //Snackbar.make(view, "Usuario existe.", Snackbar.LENGTH_SHORT).show();
                                            // Intent i = new Intent(getActivity(), Card.class);
                                            // startActivity(i);
                                        } else {
                                            dialog.dismiss();
                                            Snackbar.make(view, "Usuario no existe.", Snackbar.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        Log.e("Volley", "Invalid JSON Object.");
                                        Snackbar.make(view, "Error desconocido.", Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    dialog.dismiss();
                                    Log.e("Volley", error.toString());
                                    Snackbar.make(view, "Error en la conexion.", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                    );

                    requestQueue.add(arrReq);
                }

            }
        });

        return view;
    }

    public boolean validarCorreo(String correo){
        if (Patterns.EMAIL_ADDRESS.matcher(correo).matches() == false){
            campoCorreo.setError("Correo erroneo");
            return false;
        } else {
            campoCorreo.setError(null);
            return true;
        }
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