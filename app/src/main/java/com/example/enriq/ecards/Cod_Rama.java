package com.example.enriq.ecards;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 *
 * create an instance of this fragment.
 */
public class Cod_Rama extends Fragment {

    private TextInputLayout campoCodigo;

    Button BTNContinuar;
    EditText CodigoRAMA;
    RequestQueue requestQueue;
    String MetodoWS = "validar_rama";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cod_rama, container, false);

        BTNContinuar  = (Button) view.findViewById(R.id.Continuar);
        CodigoRAMA = (EditText) view.findViewById(R.id.Codigo);
        campoCodigo = (TextInputLayout) view.findViewById(R.id.campo_Cod_rama);

        requestQueue = Volley.newRequestQueue(getActivity());

        BTNContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean confirmCodigo = validarCodigo(CodigoRAMA);

                if (confirmCodigo){
                    final String codigo = CodigoRAMA.getText().toString();
                    String url = getString(R.string.URLWS);
                    url = url + MetodoWS;

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("codigo", codigo);

                    JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String respuesta = response.get("respuesta").toString();
                                        if(respuesta.equals("si")){
                                            SignUpFragment Fr1 = new SignUpFragment();

                                            Bundle args = new Bundle();
                                            args.putString("CODRAMA", codigo);
                                            Fr1.setArguments(args);

                                            FragmentTransaction transition = getActivity().getSupportFragmentManager().beginTransaction();
                                            transition.replace(R.id.CONTENEDOR, Fr1);
                                            transition.commit();
                                        } else {
                                            Toast.makeText(getActivity(),"Codigo erroneo", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        Log.e("Volley", "Invalid JSON Object.");
                                        Toast.makeText(getActivity(),"Error desconocido", Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Volley", error.toString());
                                    Toast.makeText(getContext(),"Error en la conexion", Toast.LENGTH_LONG).show();
                                }
                            }
                    );

                    requestQueue.add(arrReq);
                }
            }
        });

        return view;
    }

    public boolean validarCodigo(EditText codigo){
        if (TextUtils.isEmpty(codigo.getText())){
            campoCodigo.setError("Ingrese codigo");
            return false;
        } else {
            campoCodigo.setError(null);
            return true;
        }
    }

}
