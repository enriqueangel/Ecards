package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.woldev.enriq.ecards.R.*;

public class ActivityVerTarjeta extends AppCompatActivity implements View.OnClickListener {

    JSONObject DATOS;
    TextView FechaEntrega,Proyecto,TipoTarea,TiempoRealizado,Link,Descripcion;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnReportar, btnEntregar, btnRechazar, btnDesempeno;
    String color = "";
    int hour, minute;
    Calendar Time;

    String url ;

    RequestQueue requestQueue;

    boolean link_evidencia = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        url = getString(R.string.URLWS);

        try {
            DATOS = new JSONObject(getIntent().getStringExtra("DATOS"));
            color = DATOS.getString("Color");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        switch (color){
            case "blanco":
                setTheme(style.ThemeGris);
                break;
            case "naranja":
                setTheme(style.ThemeNaranja);
                break;
            case "rojo":
                setTheme(style.ThemeRojo);
                break;
            case "verde-rojo":
                setTheme(style.ThemeVerde);
                break;
            case "verde-naranja":
                setTheme(style.ThemeVerde);
                break;
            case "verde-blanco":
                setTheme(style.ThemeVerde);
                break;
            case "morado":
                setTheme(style.ThemePurple);
                break;
        }

        setContentView(layout.activity_ver_tarjeta);

        btnReportar = (FloatingActionButton) findViewById(id.btnReportar);
        btnEntregar = (FloatingActionButton) findViewById(id.btnEntregar);
        btnRechazar = (FloatingActionButton) findViewById(id.btnRechazar);
        btnDesempeno = (FloatingActionButton) findViewById(id.btnDesempeno);

        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            getSupportActionBar().setTitle(DATOS.getString("titulo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(id.collapsingtoolbar);

        actualizarContenido();

        btnEntregar.setOnClickListener(this);
        btnReportar.setOnClickListener(this);
        btnRechazar.setOnClickListener(this);
        btnDesempeno.setOnClickListener(this);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contenedor, fragment);
        transaction.commit();
    }

    private void actualizarContenido() {

        // Creamos un nuevo Bundle
        Bundle args = new Bundle();
        args.putString("DATOS", DATOS.toString());

        switch (color){
            case "blanco":
                collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.fondo_gris));
                FragmentInformacionTarjeta Fr1 = new FragmentInformacionTarjeta();
                args.putString("color", "blanco");
                Fr1.setArguments(args);
                loadFragment(Fr1);
                break;
            case "naranja":
                collapsingToolbarLayout.setBackground(getResources().getDrawable(drawable.fondo_naranja));
                FragmentInformacionTarjeta Fr2 = new FragmentInformacionTarjeta();
                args.putString("color", "naranja");
                Fr2.setArguments(args);
                loadFragment(Fr2);
                break;
            case "rojo":
                collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.fondo_rojo));
                FragmentInformacionTarjeta Fr3 = new FragmentInformacionTarjeta();
                args.putString("color", "rojo");
                Fr3.setArguments(args);
                loadFragment(Fr3);
                break;
            case "verde-rojo":
                btnRechazar.setVisibility(View.VISIBLE);
                btnDesempeno.setVisibility(View.VISIBLE);
                collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.fondo_verde_rojo));
                FragmentInformacionTester Fr4 = new FragmentInformacionTester();
                Fr4.setArguments(args);
                loadFragment(Fr4);
                break;
            case "verde-naranja":
                btnRechazar.setVisibility(View.VISIBLE);
                btnDesempeno.setVisibility(View.VISIBLE);
                collapsingToolbarLayout.setBackground(getResources().getDrawable(drawable.fondo_verde_naranja));
                FragmentInformacionTester Fr7 = new FragmentInformacionTester();
                Fr7.setArguments(args);
                loadFragment(Fr7);
                break;
            case "verde-blanco":
                btnRechazar.setVisibility(View.VISIBLE);
                btnDesempeno.setVisibility(View.VISIBLE);
                collapsingToolbarLayout.setBackground(getResources().getDrawable(drawable.fondo_verde_gris));
                FragmentInformacionTester Fr8 = new FragmentInformacionTester();
                Fr8.setArguments(args);
                loadFragment(Fr8);
                break;
            case "azul":
                FragmentInformacionReunion Fr5 = new FragmentInformacionReunion();
                Fr5.setArguments(args);
                loadFragment(Fr5);
                btnRechazar.setVisibility(View.GONE);
                break;
            case "morado":
                collapsingToolbarLayout.setBackground(getResources().getDrawable(drawable.fondo_morado));
                FragmentInformacionTarjeta Fr6 = new FragmentInformacionTarjeta();
                args.putString("color", "morado");
                Fr6.setArguments(args);
                loadFragment(Fr6);
                break;
        }
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
    public void onClick(View view) {
        switch (view.getId()){
            case id.btnDesempeno:
                crearDialogDesempeno();
                break;
            case id.btnEntregar:
                if(color.equals("verde-naranja")||color.equals("verde-rojo")||color.equals("verde-blanco"))
                    crearDialogCalificar();
                else
                    crearDialogEntregar();
                break;
            case id.btnReportar:
                crearDialogReportarHoras();
                break;
            case id.btnRechazar:
                crearDialogRechazar();
                break;
            default:
                break;
        }
    }

    private void crearDialogDesempeno() {
        final AlertDialog.Builder desempenoTarjeta = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        desempenoTarjeta.setTitle("Desempeño");

        final AlertDialog dialogCargando;
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        dialogCargando = mBuilder.create();

        dialogCargando.show();

        Map<String, String> params = new HashMap<String, String>();
        String IDtarjeta = null;
        try {
            JSONObject Tarjeta = DATOS.getJSONObject("tarjeta");
            IDtarjeta = Tarjeta.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("id", IDtarjeta);
        String urlTEmp = url+"tarjetas/desempeno";

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urlTEmp, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray DatosDesempeño = response.getJSONArray("trabajos");

                            ArrayList<Desempeno> desempenos = new ArrayList<>();

                            for (int i = 0; i < DatosDesempeño.length(); i++) {
                                JSONObject row = DatosDesempeño.getJSONObject(i);

                                Date FechaEntregada = null ;

                                String FechaEntregadaStringWs = row.getString("fecha");
                                String HorasTrabajadasTemp = row.getString("horas_trabajadas");
                                String DescripcionTemp = row.getString("descripcion");
                                String FechaEntregadaStringLocal = "";

                                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");

                                try {
                                    FechaEntregada = format2.parse(FechaEntregadaStringWs);
                                    FechaEntregadaStringLocal = parseador.format(FechaEntregada);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                desempenos.add(new Desempeno(FechaEntregadaStringLocal, HorasTrabajadasTemp, DescripcionTemp));


                            }

                            AdapterListDesempeno adapter = new AdapterListDesempeno(getApplicationContext(), desempenos);

                            desempenoTarjeta.setAdapter(adapter, null);

                            AlertDialog dialog = desempenoTarjeta.create();
                            dialog.show();

                            dialogCargando.dismiss();
                        } catch (JSONException e) {
                            Log.e("Volley", "Invalid JSON Object.");
                            dialogCargando.dismiss();
                            Toast.makeText(getApplicationContext(), "Error cargando el desempeño.", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(view, "Error desconocido.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialogCargando.dismiss();
                        Log.e("Volley", error.toString());
                        Toast.makeText(getApplicationContext(), "Error en la conexion.", Toast.LENGTH_SHORT).show();
                        // Snackbar.make(view, "Error en la conexion.", Snackbar.LENGTH_SHORT).show();
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

        desempenoTarjeta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });


    }

    private void crearDialogCalificar() {
        final AlertDialog.Builder calificarTarjeta = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        final View mView = this.getLayoutInflater().inflate(layout.dialog_calificacion, null);
        calificarTarjeta.setView(mView);
        calificarTarjeta.setTitle("Calificar tarjeta");

        calificarTarjeta.setPositiveButton("Calificar", null);

        calificarTarjeta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = calificarTarjeta.create();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView2 = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
        final AlertDialog dialogCargando = mBuilder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Calificacion realizada", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        dialog.show();
    }

    private void crearDialogRechazar() {
        final AlertDialog.Builder rechazarTarjeta = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        final View mView = this.getLayoutInflater().inflate(layout.dialog_rechazar, null);
        rechazarTarjeta.setView(mView);
        rechazarTarjeta.setTitle("Rechazar tarjeta");

        rechazarTarjeta.setPositiveButton("Rechazar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "ActivityVerTarjeta rechazada", Toast.LENGTH_LONG).show();
            }
        });

        rechazarTarjeta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = rechazarTarjeta.create();
        dialog.show();
    }

    private void crearDialogEntregar() {
        final AlertDialog.Builder entregarTarjeta = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        final View mView = this.getLayoutInflater().inflate(layout.dialog_entregar, null);
        entregarTarjeta.setView(mView);
        entregarTarjeta.setTitle("Entregar tarjeta");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView2 = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
        final AlertDialog dialogCargando = mBuilder.create();

        LinearLayout evidencia = (LinearLayout) mView.findViewById(id.evidencia);

        if (link_evidencia){
            evidencia.setVisibility(View.VISIBLE);
        }

        entregarTarjeta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogCargando.show();
                String urltemp = url+"trabajo/entregar_tarjeta";
                requestQueue = Volley.newRequestQueue(getApplicationContext());

                Map<String, String> params = new HashMap<String, String>();

                try {
                    params.put("id", DATOS.getString("_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String tipoTemp = "";

                if (color.equals("azul")){
                    tipoTemp = "reunion";
                }else{
                    tipoTemp = "tarjeta";
                }

                params.put("tipo", tipoTemp);

                JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urltemp, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String Respuesta = response.getString("respuesta");
                                    if (Respuesta.equals("si")){
                                        dialogCargando.dismiss();
                                        finish();
                                    }else{
                                        dialogCargando.dismiss();
                                        Toast.makeText(getApplicationContext(), "Error entregando la tarjeta", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    dialogCargando.dismiss();
                                    Toast.makeText(getApplicationContext(), "Error entregando la tarjeta", Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error en la conexion", Toast.LENGTH_LONG).show();
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

        entregarTarjeta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = entregarTarjeta.create();
        dialog.show();
    }

    private void crearDialogReportarHoras() {

        final AlertDialog.Builder reportarHoras = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        final View mView = this.getLayoutInflater().inflate(layout.dialog_reportar_horas, null);
        reportarHoras.setView(mView);
        reportarHoras.setTitle("Reportar horas");

        final TextView Horas = (TextView) mView.findViewById(R.id.EDThora);
        final TextView Descripcion = (TextView) mView.findViewById(id.EDTdescripcion);
        final TextInputLayout campoHoras = (TextInputLayout) mView.findViewById(id.CampoHora);
        final TextInputLayout campoDescripcion = (TextInputLayout) mView.findViewById(id.CampoDescripcion);

        //Hora
        Time = Calendar.getInstance();
        hour = 0;
        minute = 0;
        //tve.setText(hour + " : " + minute + " " + format);
        Horas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityVerTarjeta.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String horaFormateada = (hourOfDay < 10)? String.valueOf("0" + hourOfDay) : String.valueOf(hourOfDay);
                        String minutoFormateada = (minute < 10)? String.valueOf("0" + minute) : String.valueOf(minute);
                        Horas.setText(horaFormateada + ":" + minutoFormateada);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        reportarHoras.setPositiveButton("Reportar", null);

        reportarHoras.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = reportarHoras.create();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView2 = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
        final AlertDialog dialogCargando = mBuilder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean enviar = true;

                        if  (!MetodosGlobales.validarCampoVacio(Horas.getText().toString())){
                            campoHoras.setError("Debe ingresar el tiempo");
                            enviar = false;
                        }else{
                            campoHoras.setError(null);
                        }

                        if  (!MetodosGlobales.validarCampoVacio(Descripcion.getText().toString())){
                            campoDescripcion.setError("Debe ingresar una descripcion");
                            enviar = false;
                        }else{
                            campoDescripcion.setError(null);
                        }

                        if(enviar){
                            dialogCargando.show();

                            String urltemp = url+"trabajo/reportar_horas";

                            requestQueue = Volley.newRequestQueue(getApplicationContext());

                            Map<String, String> params = new HashMap<String, String>();

                            params.put("descripcion", Descripcion.getText().toString());
                            params.put("horas_trabajadas", Horas.getText().toString());

                            try {

                                String TipoTareatemp = "";

                                switch (color){
                                    case ("azul"):
                                        TipoTareatemp = "reunion";
                                    default:

                                        if(color.toLowerCase().contains("verde")){
                                            TipoTareatemp = "tester";
                                        }else{
                                            TipoTareatemp = "tarjeta";
                                        }

                                        break;
                                }

                                params.put(TipoTareatemp, DATOS.getString("_id"));
                                JSONObject TipoTareaTEMP = DATOS.getJSONObject("tipotarea");
                                params.put("tipotarea", TipoTareaTEMP.getString("_id"));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urltemp, new JSONObject(params),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                String Respuesta = response.getString("respuesta");
                                                if (Respuesta.equals("si")){
                                                    dialogCargando.dismiss();
                                                    finish();
                                                }else{
                                                    dialogCargando.dismiss();
                                                    Toast.makeText(getApplicationContext(), "Error reportando las horas", Toast.LENGTH_LONG).show();
                                                }

                                            } catch (JSONException e) {
                                                dialogCargando.dismiss();
                                                Toast.makeText(getApplicationContext(), "Error reportando las horas", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getApplicationContext(), "Error en la conexion", Toast.LENGTH_LONG).show();
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
                    }
                });
            }
        });

        dialog.show();
    }
}