package com.example.enriq.ecards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login_pin extends AppCompatActivity {

    TextView textViewbienv;
    Button buttonIngresarpin;
    EditText PIN;
    TextView TextOlvidePIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pin);

        textViewbienv = (TextView)findViewById(R.id.textViewbienv);
        buttonIngresarpin = (Button)findViewById( R.id.buttonIngresarpin);
        PIN = (EditText)findViewById( R.id.editTextIngresePIN );
        TextOlvidePIN = (TextView)findViewById(R.id.TextViewOlviPIN);

        SharedPreferences SP = getSharedPreferences("PIN",MODE_PRIVATE);


        String CorreO = SP.getString("Correo","");
        textViewbienv.setText("Bienvenido " + CorreO);


        TextOlvidePIN.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        } );


        buttonIngresarpin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences SP = getSharedPreferences("PIN",MODE_PRIVATE);
                String pin = SP.getString("Pin", "");

                if( pin.equals( PIN.getText().toString() )){

                    Intent intent = new Intent(login_pin.this, Card.class);
                    startActivity(intent);

                }else{

                    Toast ErrorConexIntern = Toast.makeText(getApplicationContext(), "El pin no es valido ", Toast.LENGTH_SHORT);
                    ErrorConexIntern.show();

                }

            }
        } );


    }
}
