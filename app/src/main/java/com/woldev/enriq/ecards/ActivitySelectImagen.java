package com.woldev.enriq.ecards;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ActivitySelectImagen extends AppCompatActivity {

    Button CAAMARA,GALERIA,Seleccionar;
    ImageView Prevista;

    String Directorio;
    Context prueba=this;

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int RespuestaCamara = 100;
    private final int RespuestaIMAGEN = 200;

    Long timestamp = System.currentTimeMillis() / 1000;
    String imageName = timestamp.toString() + ".jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_imagen);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Seleccionar Imagen");

        CAAMARA = (Button) findViewById(R.id.BTNCamara);
        GALERIA = (Button) findViewById(R.id.BTNGaleria);
        Seleccionar = (Button) findViewById(R.id.BTNSeleccionar);
        Prevista = (ImageView) findViewById(R.id.PreView);

        if (VerificaPermisos()){
            CAAMARA.setEnabled(true);
            GALERIA.setEnabled(true);
            Seleccionar.setEnabled(true);
        }else{
            CAAMARA.setEnabled(false);
            GALERIA.setEnabled(false);
            Seleccionar.setEnabled(false);
        }

        GALERIA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,RespuestaIMAGEN);

            }
        });

        CAAMARA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
                    file.mkdirs();

                    String Path = Environment.getExternalStorageDirectory() + File.separator +
                            MEDIA_DIRECTORY + File.separator + imageName;

                    File NewFile = new File(Path);

                    Intent intent=null;
                    intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                    //startActivityForResult(intent, RespuestaCamara);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        String authorities=getApplicationContext().getPackageName()+".provider";
                        Uri imageUri=FileProvider.getUriForFile(prueba,authorities,NewFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    }else
                    {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(NewFile));
                    }
                    startActivityForResult(intent, RespuestaCamara);
                }


        });

        Seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("data",Directorio);
                setResult(RESULT_OK, data);
                finish();

            }
        });
    }




    private boolean VerificaPermisos(){
       int permissionCheck1 = ContextCompat.checkSelfPermission(ActivitySelectImagen.this,
                CAMERA);
       int permissionCheck2 = ContextCompat.checkSelfPermission(ActivitySelectImagen.this,
                WRITE_EXTERNAL_STORAGE);
        int permissionCheck3 = ContextCompat.checkSelfPermission(ActivitySelectImagen.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);


        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }



        if ((permissionCheck1 ==  PackageManager.PERMISSION_DENIED )  &&
                (permissionCheck2 ==  PackageManager.PERMISSION_DENIED ) &&
                    (permissionCheck3 ==  PackageManager.PERMISSION_DENIED )) {
            //Toast.makeText(getApplicationContext(), "La aplicación no tiene permisos para acceder a la camara.", Toast.LENGTH_SHORT).show();
            requestPermissions(new String[]{CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 100);
            return false;
        }

        return true;
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED && grantResults[2]==PackageManager.PERMISSION_GRANTED) {
                CAAMARA.setEnabled(true);
                GALERIA.setEnabled(true);
                Toast.makeText(getApplicationContext(), "La aplicación tiene permisos para acceder a la camara.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "La aplicación no tiene permisos para acceder a la camara.", Toast.LENGTH_SHORT).show();
            }
        }

    }




    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
       // matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private void decodeBITMAP(String dir) {
        Bitmap original = BitmapFactory.decodeFile(dir);
        original = RotateBitmap(original, 0);
        Prevista.setImageBitmap(original);
        //Prevista.setRotation((float) 45.0);
    }

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
}



