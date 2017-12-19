package com.example.enriq.ecards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Crear_Pin extends AppCompatActivity {

    private TextInputLayout campoPin;
    private TextInputLayout campoConfirmar;

    EditText PIN;
    EditText PINCon;
    Button IngrePIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pin);

        campoPin = (TextInputLayout) findViewById(R.id.campo_pin);
        campoConfirmar = (TextInputLayout) findViewById(R.id.campo_confirmar);
        PINCon = (EditText) findViewById(R.id.editTextPINCONFIRMATION);
        PIN = (EditText) findViewById(R.id.editTextPIN);
        IngrePIN = (Button) findViewById(R.id.ButtonConfirmarPIN);

        IngrePIN.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean confirmPin = validarPin(PIN.getText().toString());
                if (compararPin(PIN.getText().toString(), PINCon.getText().toString(), confirmPin)) {
                    SharedPreferences SP = getSharedPreferences("PIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = SP.edit();
                    editor.putBoolean("User", true);

                    String Correo = getIntent().getExtras().getString("Correo");
                    editor.putString("Correo", Correo);
                    editor.putString("Pin", PIN.getText().toString());
                    editor.apply();

                    Toast.makeText(getApplicationContext(), "PIN creado correctamente", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), login_pin.class);
                    startActivity(intent);

                    finish();
                }
            }
        });
    }

    public boolean validarPin(String pin){
        if (pin.length() < 4) {
            campoPin.setError("El PIN debe ser mínimo de 4");
            return false;
        } else {
            campoPin.setError(null);
            return true;
        }
    }

    public boolean compararPin(String pin, String pin2, boolean valor){
        if (pin2.length() < 4){
            campoConfirmar.setError("El PIN debe ser mínimo de 4");
            return false;
        } else {
            campoConfirmar.setError(null);
            if (valor){
                if (!pin.equals(pin2)){
                    campoPin.setError("Los PINs deben ser iguales");
                    campoConfirmar.setError("Los PINs deben ser iguales");
                    return false;
                } else {
                    campoPin.setError(null);
                    campoConfirmar.setError(null);
                    return true;
                }
            }
            return false;
        }
    }
}
