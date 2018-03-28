package com.woldev.enriq.ecards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;

/**
 * Created by jose on 28/03/2018.
 */

public class FireBaseInstanceIDService extends FirebaseInstanceIdService {

    public static final String TAG = "NOTICIAS";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();

        enviarTokenAlServidor(token);
    }

    private void enviarTokenAlServidor(String token) {

        SharedPreferences SP = getSharedPreferences("TOKEN",MODE_PRIVATE);
        final String  tokenWS = SP.getString("token","");

        if  (tokenWS.equals("")){
            return;
        }

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);


        String urltemp = getString(R.string.URLWS)+"tokenfbput";


        Map<String, String> params = new HashMap<String, String>();
        params.put("token", token);


        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urltemp, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respuesta = response.get("respuesta").toString();
                            if(respuesta.equals("si")){

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
