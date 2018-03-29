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

        SharedPreferences SP2 = getSharedPreferences("FireBase",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = SP2.edit();

        String TokenFBtemp  = SP2.getString("TokenFB","");

        if(TokenFBtemp.equals("")){
            editor2.putString("TokenFB",token);
        }

        editor2.putString("TokenFBnuevo",token);
        editor2.apply();

    }


}
