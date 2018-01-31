package com.example.enriq.ecards;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.enriq.ecards.R.*;

public class Tarjeta extends AppCompatActivity implements View.OnClickListener {

    JSONObject DATOS;
    TextView FechaEntrega,Proyecto,TipoTarea,TiempoRealizado,Link,Descripcion;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnReportar, btnEntregar;
    String color = "blanco";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(id.collapsingtoolbar);

        actualizarContenido();

        btnEntregar.setOnClickListener(this);
        btnReportar.setOnClickListener(this);

        /*FechaEntrega = (TextView) findViewById(id.TXVfecha_entrega);
        Proyecto = (TextView) findViewById(id.TXVproyecto);
        TipoTarea = (TextView) findViewById(id.TXVtipo);
        TiempoRealizado = (TextView) findViewById(id.TXVtiempo_desarrollo);
        Link = (TextView) findViewById(id.TXVlink);
        Descripcion = (TextView) findViewById(id.TXVdescripcion);

        try {
            DATOS = new JSONObject(getIntent().getStringExtra("DATOS"));

            String dtStart = DATOS.getString("plazo");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy");
            Date fechaFin ;
            String FechaFinString = "Error obteniendo fecha.";

            try {
                fechaFin = format.parse(dtStart);
                FechaFinString = formateador.format(fechaFin);

            } catch (ParseException e) {

                e.printStackTrace();
            }

             FechaEntrega.setText(FechaFinString);

            //Proyecto.setText(DATOS.get("nombres").toString());

            JSONObject TipoTareaTEMP = DATOS.getJSONObject("tipotarea");
            String TipoTEMP = TipoTareaTEMP.getString("nombre");
            TipoTarea.setText(TipoTEMP);

            TiempoRealizado.setText(DATOS.get("tiempo_trabajado").toString());
            Link.setText(DATOS.get("link").toString());
            Descripcion.setText(DATOS.get("descripcion").toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contenedor, fragment);
        transaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void actualizarContenido() {
        switch (color){
            case "blanco":
                collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.fondo_gris));
                TarjetaFragment Fr1 = new TarjetaFragment();
                loadFragment(Fr1);
                break;
            case "naranja":
                collapsingToolbarLayout.setBackground(getResources().getDrawable(drawable.fondo_naranja));
                TarjetaFragment Fr2 = new TarjetaFragment();
                loadFragment(Fr2);
                break;
            case "rojo":
                collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.fondo_rojo));
                TarjetaFragment Fr3 = new TarjetaFragment();
                loadFragment(Fr3);
                break;
            case "verde":
                collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.fondo_verde));
                TarjetaFragment Fr4 = new TarjetaFragment();
                loadFragment(Fr4);
                break;
            case "azul":
                ReunionFragment Fr5 = new ReunionFragment();
                loadFragment(Fr5);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Intent i = new Intent(Tarjeta.this, Card.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case id.btnEntregar:
                Toast.makeText(getApplicationContext(), "Entregar tarjeta", Toast.LENGTH_LONG).show();
                break;
            case id.btnReportar:
                Toast.makeText(getApplicationContext(), "Reportar horas", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
