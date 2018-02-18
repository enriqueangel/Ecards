package com.example.enriq.ecards;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.enriq.ecards.R.*;

public class Tarjeta extends AppCompatActivity implements View.OnClickListener {

    JSONObject DATOS;
    TextView FechaEntrega,Proyecto,TipoTarea,TiempoRealizado,Link,Descripcion;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnReportar, btnEntregar, btnRechazar;
    String color = "";
    int hour, minute;
    Calendar Time;

    boolean link_evidencia = false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            case "verde":
                setTheme(style.ThemeVerde);
                break;
        }

        setContentView(layout.activity_tarjeta);

        btnReportar = (FloatingActionButton) findViewById(id.btnReportar);
        btnEntregar = (FloatingActionButton) findViewById(id.btnEntregar);
        btnRechazar = (FloatingActionButton) findViewById(id.btnRechazar);

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
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contenedor, fragment);
        transaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void actualizarContenido() {

        // Creamos un nuevo Bundle
        Bundle args = new Bundle();
        args.putString("DATOS", DATOS.toString());

        switch (color){
            case "blanco":
                collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.fondo_gris));
                TarjetaFragment Fr1 = new TarjetaFragment();
                Fr1.setArguments(args);
                loadFragment(Fr1);
                break;
            case "naranja":
                collapsingToolbarLayout.setBackground(getResources().getDrawable(drawable.fondo_naranja));
                TarjetaFragment Fr2 = new TarjetaFragment();
                Fr2.setArguments(args);
                loadFragment(Fr2);
                break;
            case "rojo":
                collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.fondo_rojo));
                TarjetaFragment Fr3 = new TarjetaFragment();
                Fr3.setArguments(args);
                loadFragment(Fr3);
                break;
            case "verde":
                collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.fondo_verde));
                TarjetaFragment Fr4 = new TarjetaFragment();
                Fr4.setArguments(args);
                loadFragment(Fr4);
                break;
            case "azul":
                ReunionFragment Fr5 = new ReunionFragment();
                Fr5.setArguments(args);
                loadFragment(Fr5);
                btnRechazar.setVisibility(View.GONE);
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
            case id.btnEntregar:
                crearDialogEntregar();
                Toast.makeText(getApplicationContext(), "Entregar tarjeta", Toast.LENGTH_LONG).show();
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

    private void crearDialogRechazar() {
        final AlertDialog.Builder rechazarTarjeta = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        final View mView = this.getLayoutInflater().inflate(layout.dialog_rechazar, null);
        rechazarTarjeta.setView(mView);
        rechazarTarjeta.setTitle("Rechazar tarjeta");

        rechazarTarjeta.setPositiveButton("Rechazar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Tarjeta rechazada", Toast.LENGTH_LONG).show();
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

        LinearLayout evidencia = (LinearLayout) mView.findViewById(id.evidencia);

        if (link_evidencia){
            evidencia.setVisibility(View.VISIBLE);
        }

        entregarTarjeta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Tarjeta entrega", Toast.LENGTH_LONG).show();
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

        //Hora
        Time = Calendar.getInstance();
        hour = Time.get(Calendar.HOUR_OF_DAY);
        minute = Time.get(Calendar.MINUTE);
        //tve.setText(hour + " : " + minute + " " + format);
        Horas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Tarjeta.this, new TimePickerDialog.OnTimeSetListener() {
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

        reportarHoras.setPositiveButton("Reportar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Reportar horas", Toast.LENGTH_LONG).show();
            }
        });

        reportarHoras.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = reportarHoras.create();
        dialog.show();
    }
}
