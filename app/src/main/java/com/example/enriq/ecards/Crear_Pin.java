package com.example.enriq.ecards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Crear_Pin extends AppCompatActivity {

    EditText PIN;
    EditText PINCon;
    Button IngrePIN;



    public boolean validarPIN(String PIN, String PIN2){

        return PIN.length() == 4 && PIN2.length() == 4 && PIN.equals( PIN2 );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear__pin);

        PINCon = (EditText) findViewById(R.id.editTextPINCONFIRMATION);
        PIN = (EditText) findViewById(R.id.editTextPIN);
        IngrePIN = (Button) findViewById(R.id.ButtonConfirmarPIN);



        IngrePIN.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validarPIN(PIN.getText().toString(),PINCon.getText().toString())){

                    SharedPreferences SP = getSharedPreferences("PIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = SP.edit();
                    editor.putBoolean("User",true);

                    String Correo = getIntent().getExtras().getString("Correo");


                    editor.putString("Correo",Correo);
                    editor.putString( "Pin",PIN.getText().toString() );

                    editor.apply();




                    Toast inscripcionrealizada = Toast.makeText( getApplicationContext(), "Inscripcion realizada con exito ", Toast.LENGTH_LONG);
                    inscripcionrealizada.show();

                    Intent intent = new Intent(getApplicationContext(), login_pin.class);



                    startActivity(intent);



                    finish();



                }else{
                    Toast ErrorPIN = Toast.makeText( getApplicationContext() , "Verifique los PIN no coinciden ", Toast.LENGTH_SHORT);
                    ErrorPIN.show();
                }



            }
        } );
    }
}
