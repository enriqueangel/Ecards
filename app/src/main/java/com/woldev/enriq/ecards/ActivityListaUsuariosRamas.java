package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
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
import java.util.List;
import java.util.Map;

public class ActivityListaUsuariosRamas extends AppCompatActivity implements View.OnClickListener {

    private ExpandableListView listView;
    private AdapterExpandableListUsuariosRamas listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    AlertDialog dialog;
    RequestQueue requestQueue;
    String url;
    JSONArray Usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios_rama);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Usuarios");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();

        listView = (ExpandableListView) findViewById(R.id.usuarios);
    }

    @Override
    protected void onStart() {
        TraerUsuarios();
        super.onStart();
    }

    private void TraerUsuarios() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        dialog.show();

        url = getString(R.string.URLWS);
        url = url+"user/lista_rama";
        requestQueue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap<String, String>();
        params.put("rol", "SUPERUSER");

        final JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Usuarios = response.getJSONArray("users");
                            CargarUsuarios();
                            // Toast.makeText(getApplicationContext(),Usuarios.toString(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
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
    }

    private void CargarUsuarios() throws JSONException {
        int contTemp = 0;
        String UltimaRama = "";
        List<String> usuariosTemp = new ArrayList<>();

        for (int i = 0; i < Usuarios.length(); i++) {
            JSONObject row = Usuarios.getJSONObject(i);
            String NombresTEmp = row.getString("nombres");
            String ApellidosTEmp = row.getString("apellidos");
            String NombreMostrar = NombresTEmp+" "+ApellidosTEmp;

            JSONArray RamaTemp = row.getJSONArray("areas");
            String AreaTemp = RamaTemp.getJSONObject(0).getString("nombre");
            if (i == 0){
                UltimaRama = AreaTemp;
                listDataHeader.add(UltimaRama);
            }else{
                if (!UltimaRama.equals(AreaTemp)){
                    UltimaRama = AreaTemp;
                    listHash.put(listDataHeader.get(contTemp), usuariosTemp);

                    contTemp++;
                    listDataHeader.add(UltimaRama);

                    usuariosTemp = new ArrayList<>();
                }
            }
            usuariosTemp.add(NombreMostrar);
        }

        listHash.put(listDataHeader.get(contTemp), usuariosTemp);

        listAdapter = new AdapterExpandableListUsuariosRamas(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                String item = listHash.get(listDataHeader.get(i)).get(i1);
                Toast.makeText(getApplication(), item, Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent i;
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
