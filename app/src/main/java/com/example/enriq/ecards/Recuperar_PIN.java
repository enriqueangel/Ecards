package com.example.enriq.ecards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Recuperar_PIN extends AppCompatActivity {


    TextView Correo;
    Button BTRecuperarPIN;
    String CorreO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar__pin);

        BTRecuperarPIN = (Button)findViewById( R.id.BotonRecuperar);

        SharedPreferences SP = getSharedPreferences("PIN",MODE_PRIVATE);
        CorreO = SP.getString("Correo","");
        Correo = (TextView) findViewById(R.id.TvCorreo);
        Correo.setText(CorreO);

        BTRecuperarPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Recuperar_PIN.this, Crear_Pin.class);
                intent.putExtra( "Correo", CorreO );
                startActivity(intent);

                finish();
            }
        });

    }
}
