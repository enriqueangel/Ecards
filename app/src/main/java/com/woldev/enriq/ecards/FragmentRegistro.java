package com.woldev.enriq.ecards;

/**
 * Created by enriq on 14/12/2017.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentRegistro extends Fragment {

    private TextInputLayout campoCorreo;
    private TextInputLayout campoContrasena;
    private TextInputLayout campoNombre;
    private TextInputLayout campoApellido;
    private TextInputLayout campoTelefono;
    private TextInputLayout campoConfirmar;

    EditText correo, contrasena, nombre, apellido, telefono, confirmar;
    Button btnSignUp;
    RequestQueue requestQueue;
    Activity Actividad;

    String baseUrl = "https://webserver-enriqeangel.c9users.io/";
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        final String CODIGORAMA = getArguments().getString("CODRAMA");

        campoCorreo = (TextInputLayout) view.findViewById(R.id.campo_correo);
        campoContrasena = (TextInputLayout) view.findViewById(R.id.campo_contrasena);
        campoNombre = (TextInputLayout) view.findViewById(R.id.campo_nombre);
        campoApellido = (TextInputLayout) view.findViewById(R.id.campo_apellido);
        campoTelefono = (TextInputLayout) view.findViewById(R.id.campo_telefono);
        campoConfirmar = (TextInputLayout) view.findViewById(R.id.campo_confirmar);

        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);

        correo = (EditText) view.findViewById(R.id.correo);
        contrasena = (EditText) view.findViewById(R.id.contrasena);
        nombre = (EditText) view.findViewById(R.id.nombre);
        apellido = (EditText) view.findViewById(R.id.apellido);
        telefono = (EditText) view.findViewById(R.id.telefono);
        confirmar = (EditText) view.findViewById(R.id.confirmar);

        requestQueue = Volley.newRequestQueue(getActivity());

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean confirmNombre = validarNombre(nombre);
                boolean confirmApellido = validarApellido(apellido);
                boolean confirmTelefono = validarTelefono(telefono.getText().toString());
                boolean confirmCorreo = validarCorreo(correo.getText().toString());
                boolean confirmContrasena = validarContraseña(contrasena);
                boolean confirmConfirm = validarConfirm(confirmar, contrasena, confirmContrasena);

                if (confirmNombre && confirmApellido && confirmTelefono && confirmCorreo && confirmContrasena && confirmConfirm){
                    dialog.show();

                    final String correo_inp = correo.getText().toString();
                    final String password_inp = contrasena.getText().toString();
                    final String nombre_inp = nombre.getText().toString();
                    final String apellido_inp = apellido.getText().toString();
                    final String telefono_inp = telefono.getText().toString();

                    url = baseUrl + "registro";

                    String tokenFB = FirebaseInstanceId.getInstance().getToken();

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("correo", correo_inp);
                    params.put("password", password_inp);
                    params.put("nombres", nombre_inp);
                    params.put("apellidos", apellido_inp);
                    params.put("telefono", telefono_inp);
                    params.put("area", CODIGORAMA);
                    params.put("TokenFB", tokenFB);

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

                                            SharedPreferences SP = Actividad.getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = SP.edit();
                                            editor.putString("token",token);
                                            editor.apply();

                                            Intent intent = new Intent(getActivity(), ActivityCrearPin.class);
                                            intent.putExtra( "Correo", correo_inp);
                                            startActivity(intent);
                                            getActivity().finish();
                                        } else if (respuesta.equals("no")){
                                            dialog.dismiss();
                                            Snackbar.make(view, "Error en el registro.", Snackbar.LENGTH_SHORT).show();
                                        } else {
                                            dialog.dismiss();
                                            Snackbar.make(view, respuesta, Snackbar.LENGTH_LONG).show();
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

    public boolean validarNombre(EditText nombre){
        if (TextUtils.isEmpty(nombre.getText())){
            campoNombre.setError("Ingrese nombre(s)");
            return false;
        } else {
            campoNombre.setError(null);
            return true;
        }
    }

    public boolean validarApellido(EditText apellido){
        if (TextUtils.isEmpty(apellido.getText())){
            campoApellido.setError("Ingrese apellido(s)");
            return false;
        } else {
            campoApellido.setError(null);
            return true;
        }
    }

    public boolean validarTelefono(String telefono){
        if (!Patterns.PHONE.matcher(telefono).matches()){
            campoTelefono.setError("Telefono erroneo");
            return false;
        } else {
            campoTelefono.setError(null);
            return true;
        }
    }

    public boolean validarCorreo(String correo){
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            campoCorreo.setError("Correo erroneo");
            return false;
        } else {
            campoCorreo.setError(null);
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
