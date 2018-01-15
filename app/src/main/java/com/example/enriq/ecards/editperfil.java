package com.example.enriq.ecards;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class editperfil extends AppCompatActivity {

    JSONObject DATOS;
    TextInputEditText Nombres,Apellidos,Telefono;
    Button GuardarCambios,EditarFOTO;
    ImageView Foto;
    RequestQueue requestQueue;
    String MetodoWS = "user/modificar_perfil";

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


        Nombres = (TextInputEditText) findViewById(R.id.TXTEDTnombre);
        Apellidos = (TextInputEditText) findViewById(R.id.TXTEDTapellido);
        Telefono = (TextInputEditText) findViewById(R.id.TXTEDTTelefono);
        GuardarCambios = (Button) findViewById(R.id.BTNGuardarCambios);
        EditarFOTO = (Button) findViewById(R.id.BTNEditarFoto);
        Foto = (ImageView) findViewById(R.id.ImgVFoto);

        requestQueue = Volley.newRequestQueue(this);

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

                dialog.show();
                final String nombresTEMP = Nombres.getText().toString();
                final String apellidosTEMP = Apellidos.getText().toString();
                final String telefonoTEMP = Telefono.getText().toString();

                String url = getString(R.string.URLWS);
                url = url + MetodoWS;

                Map<String, String> params = new HashMap<String, String>();
                params.put("nombres", nombresTEMP);
                params.put("apellidos", apellidosTEMP);
                params.put("telefono", telefonoTEMP);

                JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String respuesta = response.get("respuesta").toString();
                                    if(respuesta.equals("si")){
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Datos modificados.", Toast.LENGTH_SHORT).show();
                                        finish();

                                    } else {
                                        dialog.dismiss();
                                        Snackbar.make(view, "Error modificando los datos.", Snackbar.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                    Snackbar.make(view, "Error desconocido.", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.dismiss();
                                Log.e("Volley", error.toString());
                                Snackbar.make(view, "Error en la conexion.", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                ){
                    /*
                    /**
                     * Passing some request headers
                     * */
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        SharedPreferences SP = getSharedPreferences("TOKEN",MODE_PRIVATE);
                        String tokenTemp = SP.getString("token","");
                        headers.put("token", tokenTemp);
                        return headers;
                    }
                };

                requestQueue.add(arrReq);

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
