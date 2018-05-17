package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityRama extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton btnEditar;
    String url;
    RequestQueue requestQueue;

    String NombreRama,CodRama;
    int CantUsuariosRama;
    TextView RamaNombre;
    TextView Codigo;
    TextView Cantidad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rama);

        RamaNombre = (TextView) findViewById(R.id.Camponombrerama);
        Codigo = (TextView) findViewById(R.id.Campocodigo);
        Cantidad = (TextView) findViewById(R.id.CampoCantidad);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Rama");

        btnEditar = (FloatingActionButton) findViewById(R.id.BTNEditarinformacion);
        btnEditar.setOnClickListener(this);

        String IDRama = "";

        if(getIntent().getExtras()!=null){
             IDRama =(getIntent().getExtras().getString("IDRAMA"));
        }

        url = getString(R.string.URLWS);
        url = url+"ramas/informacion";

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView2 = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
        final AlertDialog dialogCargar = mBuilder.create();
        dialogCargar.show();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Map<String, String> params = new HashMap<String, String>();

        if(!IDRama.equals("")){
            params.put("id", IDRama);
        }

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject Respuesta = response.getJSONObject("rama");
                            NombreRama = Respuesta.getString("nombre");
                            CodRama = Respuesta.getString("codigo");
                            CantUsuariosRama = response.getInt("cantidad");

                            RamaNombre.setText(NombreRama);
                            Codigo.setText(CodRama);
                            Cantidad.setText(String.valueOf(CantUsuariosRama));
                            dialogCargar.dismiss();
                        } catch (JSONException e) {
                            dialogCargar.dismiss();
                            Log.e("Volley", "Invalid JSON Object.");
                            Toast.makeText(getApplicationContext(), "Error desconocido.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialogCargar.dismiss();
                        Log.e("Volley", error.toString());
                        Toast.makeText(getApplicationContext(), "Error en la conexion.", Toast.LENGTH_SHORT).show();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void crearDialogEditRama() {
        final AlertDialog.Builder EditRama = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        final View mView = this.getLayoutInflater().inflate(R.layout.dialog_edit_rama, null);
        EditRama.setView(mView);
        EditRama.setTitle("Editar ActivityRama");

        final TextView RamaNombre = (TextView) mView.findViewById(R.id.EDTnombrer);
        final TextView Codigo = (TextView) mView.findViewById(R.id.EDTcodigo);
        final TextInputLayout campoNombrerama = (TextInputLayout) mView.findViewById(R.id.CamponombreRama);
        final TextInputLayout campoCodigo = (TextInputLayout) mView.findViewById(R.id.Campocodigo);

        RamaNombre.setText(NombreRama);
        Codigo.setText(CodRama);

        EditRama.setPositiveButton("Editar", null);

        EditRama.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = EditRama.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Editar", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        dialog.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BTNEditarinformacion:
                crearDialogEditRama();
        }
    }
}
