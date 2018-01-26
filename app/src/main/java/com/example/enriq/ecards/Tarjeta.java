package com.example.enriq.ecards;

import android.content.Intent;
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

public class Tarjeta extends AppCompatActivity implements View.OnClickListener {

    JSONObject DATOS;
    TextView FechaEntrega,Proyecto,TipoTarea,TiempoRealizado,Link,Descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);

        FloatingActionButton btnReportar = (FloatingActionButton) findViewById(R.id.btnReportar);
        FloatingActionButton btnEntregar = (FloatingActionButton) findViewById(R.id.btnEntregar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnEntregar.setOnClickListener(this);
        btnReportar.setOnClickListener(this);

        FechaEntrega = (TextView) findViewById(R.id.TXVfecha_entrega);
        Proyecto = (TextView) findViewById(R.id.TXVproyecto);
        TipoTarea = (TextView) findViewById(R.id.TXVtipo);
        TiempoRealizado = (TextView) findViewById(R.id.TXVtiempo_desarrollo);
        Link = (TextView) findViewById(R.id.TXVlink);
        Descripcion = (TextView) findViewById(R.id.TXVdescripcion);

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
            case R.id.btnEntregar:
                Toast.makeText(getApplicationContext(), "Entregar tarjeta", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnReportar:
                Toast.makeText(getApplicationContext(), "Reportar horas", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
