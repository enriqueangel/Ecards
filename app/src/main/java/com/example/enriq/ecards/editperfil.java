package com.example.enriq.ecards;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class editperfil extends AppCompatActivity {

    JSONObject DATOS;
    TextInputEditText Correo;
    Button GuardarCambios,EditarFOTO;
    ImageView Foto;

    private final int ImagenNueva = 999;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case ImagenNueva:
                if (resultCode == RESULT_OK){
                    Uri path = data.getData();

                    try {

                        Bitmap original = BitmapFactory.decodeFile(path.toString());
                        original = RotateBitmap(original, 90);
                        Foto.setImageBitmap(original);

                    }catch (Exception e){

                        Foto.setImageURI(path);
                    }




                }


                break;




        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editperfil);

        Correo = (TextInputEditText) findViewById(R.id.TXTEDTcorreo);
        GuardarCambios = (Button) findViewById(R.id.BTNGuardarCambios);
        EditarFOTO = (Button) findViewById(R.id.BTNEditarFoto);
        Foto = (ImageView) findViewById(R.id.ImgVFoto);

        try {
            DATOS = new JSONObject(getIntent().getStringExtra("DATOS"));
            Correo.setText(DATOS.get("email").toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        GuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EditarFOTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editperfil.this, SelectImagen.class);
                startActivityForResult(intent,ImagenNueva);
            }
        });
    }
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
