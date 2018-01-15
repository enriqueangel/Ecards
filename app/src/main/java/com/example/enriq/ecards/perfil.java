package com.example.enriq.ecards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class perfil extends AppCompatActivity {

    JSONObject DATOS;
    TextView Correo,Nombres,Apellidos,Telefono,Rama,HrsLaborales;
    Button EditarDAtos;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_perfil);

        Correo = (TextView) findViewById(R.id.TXTVCorreo);
        Nombres = (TextView) findViewById(R.id.TXTVNombres);
        Apellidos = (TextView) findViewById(R.id.TXTVApellidos);
        Telefono = (TextView) findViewById(R.id.TXTVTelefono);
        Rama = (TextView) findViewById(R.id.TXTVRama);
        HrsLaborales = (TextView) findViewById(R.id.TXTVHrsLaborales);
        EditarDAtos = (Button) findViewById(R.id.BTNEditarDatos);

        try {
            DATOS = new JSONObject(getIntent().getStringExtra("DATOS"));

            Correo.setText(DATOS.get("email").toString());
            Nombres.setText(DATOS.get("nombres").toString());
            Apellidos.setText(DATOS.get("apellidos").toString());
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
                finish();
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

}



