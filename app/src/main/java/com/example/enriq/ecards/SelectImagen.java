package com.example.enriq.ecards;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SelectImagen extends AppCompatActivity {

    Button CAMARA,GALERIA,Seleccionar;
    ImageView Prevista;

    String Directorio;

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int RespuestaCamara = 100;
    private final int RespuestaIMAGEN = 200;

    Long timestamp = System.currentTimeMillis() / 1000;
    String imageName = timestamp.toString() + ".jpg";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RespuestaCamara:
                if (resultCode == RESULT_OK){
                    String dir = Environment.getExternalStorageDirectory() + File.separator +
                            MEDIA_DIRECTORY + File.separator + imageName;

                    Directorio = dir;

                    decodeBITMAP(dir);
                }


                break;


            case RespuestaIMAGEN:
                if (resultCode == RESULT_OK){
                    Uri path = data.getData();
                    Directorio = path.toString();
                    Prevista.setImageURI(path);
                }


                break;


        }
    }

    private void decodeBITMAP(String dir) {


        Bitmap original = BitmapFactory.decodeFile(dir);

        original = RotateBitmap(original, 90);


        Prevista.setImageBitmap(original);



        //Prevista.setRotation((float) 45.0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_imagen);

        CAMARA = (Button) findViewById(R.id.BTNCamara);
        GALERIA = (Button) findViewById(R.id.BTNGaleria);
        Seleccionar = (Button) findViewById(R.id.BTNSeleccionar);
        Prevista = (ImageView) findViewById(R.id.PreView);



        GALERIA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!VerificaPermisos(false,true,true)) {
                    return;
                }

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,RespuestaIMAGEN);
            }
        });

        CAMARA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!VerificaPermisos(true,true,true)) {
                    return;
                }



                File file = new File(Environment.getExternalStorageDirectory(),MEDIA_DIRECTORY);
                file.mkdirs();



                String Path  = Environment.getExternalStorageDirectory() + File.separator +
                        MEDIA_DIRECTORY + File.separator + imageName;

                File NewFile = new  File(Path);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(NewFile));
                startActivityForResult(intent,RespuestaCamara);


            }
        });

        Seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent data = new Intent();

                data.setData(Uri.parse(Directorio));

                setResult(RESULT_OK, data);

                finish();
            }
        });


    }

    private boolean VerificaPermisos(boolean camara,boolean WES,boolean RES){

        int permissionCheck1 = ContextCompat.checkSelfPermission(SelectImagen.this,
                Manifest.permission.CAMERA);
        int permissionCheck2 = ContextCompat.checkSelfPermission(SelectImagen.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck3 = ContextCompat.checkSelfPermission(SelectImagen.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck1 ==  PackageManager.PERMISSION_DENIED && camara) {
            Toast.makeText(getApplicationContext(), "La aplicación no tiene permisos para acceder a la camara.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (permissionCheck2 ==  PackageManager.PERMISSION_DENIED && WES){
            Toast.makeText(getApplicationContext(), "La aplicación no tiene permisos para modificar el almacenamiento.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (permissionCheck3 ==  PackageManager.PERMISSION_DENIED && RES){
            Toast.makeText(getApplicationContext(), "La aplicación no tiene permisos para leer el almacenamiento..", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}



