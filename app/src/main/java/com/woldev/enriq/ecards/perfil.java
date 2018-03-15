package com.woldev.enriq.ecards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class perfil extends AppCompatActivity {

    JSONObject DATOS;
    TextView Correo,Nombres,Apellidos,Telefono,Rama,HrsLaborales;
    FloatingActionButton EditarDAtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Toolbar toolbar = findViewById(R.id.toolbarPerfil);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Correo = (TextView) findViewById(R.id.TXTVCorreo);
        Telefono = (TextView) findViewById(R.id.TXTVTelefono);
        Rama = (TextView) findViewById(R.id.TXTVRama);
        HrsLaborales = (TextView) findViewById(R.id.TXTVHrsLaborales);
        EditarDAtos = (FloatingActionButton) findViewById(R.id.BTNEditarDatos);

        try {
            DATOS = new JSONObject(getIntent().getStringExtra("DATOS"));
            String NombreTemp = DATOS.get("nombres").toString() + " " + DATOS.get("apellidos").toString();

            getSupportActionBar().setTitle(NombreTemp);
            Correo.setText(DATOS.get("email").toString());
            Telefono.setText(DATOS.get("telefono").toString());
            Rama.setText(GetRamas(DATOS.getJSONArray("areas")));
            HrsLaborales.setText(DATOS.get("horas_contratadas").toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        EditarDAtos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(perfil.this, editperfil.class);
                intent.putExtra( "DATOS", DATOS.toString());
                startActivity(intent);
            }
        });
    }

    private String GetRamas(JSONArray jsonArray) throws JSONException {
        String Ramas = "";
        Boolean Primero = true;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject Rama = jsonArray.getJSONObject(i);
            if (!Primero){
                Ramas += ", ";
            };

            Ramas += Rama.getString("nombre");
            Primero = false;
        }
        return Ramas;
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}



