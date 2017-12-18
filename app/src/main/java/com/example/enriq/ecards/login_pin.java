package com.example.enriq.ecards;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login_pin extends AppCompatActivity {

    private TextInputLayout campoPin;

    TextView textViewbienv;
    Button buttonIngresarpin;
    EditText PIN;
    TextView TextOlvidePIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pin);

        campoPin = (TextInputLayout) findViewById(R.id.campo_pin);
        textViewbienv = (TextView)findViewById(R.id.textViewbienv);
        buttonIngresarpin = (Button)findViewById(R.id.buttonIngresarpin);
        PIN = (EditText)findViewById(R.id.editTextIngresePIN );
        TextOlvidePIN = (TextView)findViewById(R.id.TextViewOlviPIN);

        SharedPreferences SP = getSharedPreferences("PIN",MODE_PRIVATE);
        String CorreO = SP.getString("Correo","");
        textViewbienv.setText(CorreO);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        TextOlvidePIN.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_pin.this, Recuperar_PIN.class);
                startActivity(intent);
            }
        });

        buttonIngresarpin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences SP = getSharedPreferences("PIN",MODE_PRIVATE);
                String pin = SP.getString("Pin", "");

                if(pin.equals( PIN.getText().toString())){
                    dialog.show();
                    campoPin.setError(null);
                    Intent intent = new Intent(login_pin.this, Card.class);
                    startActivity(intent);
                } else {
                    dialog.dismiss();
                    campoPin.setError("El PIN no es valido");
                }
            }
        });
    }
}
