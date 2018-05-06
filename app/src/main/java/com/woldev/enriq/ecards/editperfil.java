package com.woldev.enriq.ecards;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentBreadCrumbs;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

//la imagen se guarda en firebase y le pasamos al servidor la url del campo de firebase donde se encuentra la imagen

public class editperfil extends AppCompatActivity {

    JSONObject DATOS;
    TextInputEditText Nombres, Apellidos, Telefono;
    Button GuardarCambios, EditarFOTO;
    ImageView Foto;
    RequestQueue requestQueue;
    String MetodoWS = "user/modificar_perfil";

    private Uri filePath;
    Uri path;
    private StorageReference storageReference;
    ProgressDialog progressDialog;
    String tipo,urlGaleria;
    Long timestamp = System.currentTimeMillis() / 1000;
    String name = timestamp.toString();


    private final int ImagenNueva = 999;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ImagenNueva:
                if (resultCode == RESULT_OK) {

                    String datos = data.getStringExtra("data");
                    path = Uri.parse(datos);




                    try {
                        Bitmap original = BitmapFactory.decodeFile(path.toString());
                        original = RotateBitmap(original, 0);
                        Foto.setImageBitmap(original);
                    } catch (Exception e) {
                        Foto.setImageURI(path);
                    }
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Editar Perfil");

        Nombres = (TextInputEditText) findViewById(R.id.TXTEDTnombre);
        Apellidos = (TextInputEditText) findViewById(R.id.TXTEDTapellido);
        Telefono = (TextInputEditText) findViewById(R.id.TXTEDTTelefono);
        GuardarCambios = (Button) findViewById(R.id.BTNGuardarCambios);
        EditarFOTO = (Button) findViewById(R.id.BTNEditarFoto);
        Foto = (ImageView) findViewById(R.id.ImgVFoto);

        requestQueue = Volley.newRequestQueue(this);
        storageReference = FirebaseStorage.getInstance().getReference();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        try {
            DATOS = new JSONObject(getIntent().getStringExtra("DATOS"));
            Nombres.setText(DATOS.get("nombres").toString());
            Apellidos.setText(DATOS.get("apellidos").toString());
            Telefono.setText(DATOS.get("telefono").toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        GuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                   uploadFile();
                if (!TextUtils.isEmpty("")) {
                    try {
                        crearPublicacion();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
              }
            }
        });

        EditarFOTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editperfil.this, SelectImagen.class);
                startActivityForResult(intent, ImagenNueva);
            }
        });
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void uploadFile() {
        //if there is a file to upload

        //displaying a progress dialog while upload is going on
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setTitle("Subiendo...");
        pDialog.show();
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);

        String ext = ".jpg";


        StorageReference riversRef = storageReference.child("images/" + name + ext);
        riversRef.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //if the upload is successfull
                                //hiding the progress dialog
                                pDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "upload", Toast.LENGTH_LONG).show();

                                //and displaying a success toast
                                urlGaleria = taskSnapshot.getMetadata().getDownloadUrl().toString();

                                try {
                                    crearPublicacion();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                //if the upload is not successfull
                                //hiding the progress dialog
                                pDialog.dismiss();

                                //and displaying error message
                                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //calculating progress percentage
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        //displaying percentage in progress dialog
                        pDialog.setMessage("Subiendo " + ((int) progress) + "%...");

                    }
                });
    }



    private void crearPublicacion() throws JSONException {

        final ProgressDialog prDialog = new ProgressDialog(this);
        prDialog.setTitle("Publicación");
        prDialog.setMessage("Creando publicación...");
        prDialog.setCanceledOnTouchOutside(false);
        prDialog.setCancelable(false);

        //dialog.show();

        final String nombresTEMP = Nombres.getText().toString();
        final String apellidosTEMP = Apellidos.getText().toString();
        final String telefonoTEMP = Telefono.getText().toString();
        final String FotoTEMP = urlGaleria;

        String url = getString(R.string.URLWS);
        url = url + MetodoWS;

        Map<String, String> params = new HashMap<String, String>();
        params.put("nombres", nombresTEMP);
        params.put("apellidos", apellidosTEMP);
        params.put("telefono", telefonoTEMP);
        params.put("imagen", FotoTEMP );

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respuesta = response.get("respuesta").toString();
                            if (respuesta.equals("si")) {
                                prDialog.dismiss();

                                Toast.makeText(getApplicationContext(), "Datos modificados.", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                prDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Error modificando los datos.", Snackbar.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("Volley", "Invalid JSON Object.");
                            Toast.makeText(getApplicationContext(), "Error desconocido.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        prDialog.dismiss();
                        Log.e("Volley", error.toString());
                        Toast.makeText(getApplicationContext(), "Error en la conexion.", Snackbar.LENGTH_SHORT).show();
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                SharedPreferences SP = getSharedPreferences("TOKEN", MODE_PRIVATE);
                String tokenTemp = SP.getString("token", "");
                headers.put("token", tokenTemp);
                return headers;
            }
        };

        requestQueue.add(arrReq);

    }
}