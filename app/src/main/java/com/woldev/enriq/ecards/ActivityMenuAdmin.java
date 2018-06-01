package com.woldev.enriq.ecards;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ActivityMenuAdmin extends AppCompatActivity implements View.OnClickListener{

    private VariablesGlobales globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        globalVariable = (VariablesGlobales) getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(toolbar);

        CardView crearTarjeta = (CardView) findViewById(R.id.crear_tarjeta);
        CardView crearReunion = (CardView) findViewById(R.id.crear_reunion);
        CardView usuarios = (CardView) findViewById(R.id.usuarios);
        CardView dashboard = (CardView) findViewById(R.id.dashboard);
        CardView corte = (CardView) findViewById(R.id.corte);

        crearTarjeta.setOnClickListener(this);
        crearReunion.setOnClickListener(this);
        usuarios.setOnClickListener(this);
        dashboard.setOnClickListener(this);
        corte.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.crear_tarjeta:
                i = new Intent(ActivityMenuAdmin.this, ActivityCrearTarjeta.class);
                i.putExtra("TIPO", "ADMIN");
                startActivity(i);
                break;
            case R.id.crear_reunion:
                i = new Intent(ActivityMenuAdmin.this, ActivityCrearReunion.class);
                i.putExtra("TIPO", "ADMIN");
                startActivity(i);
                break;
            case R.id.usuarios:
                i = new Intent(ActivityMenuAdmin.this, ActivityListaUsuarios.class);
                startActivity(i);
                break;
            case R.id.dashboard:
                i = new Intent(this, ActivityDashboard.class);
                String areasTEmp = globalVariable.getAreas();
                i.putExtra( "Areas", areasTEmp);
                startActivity(i);
                finish();
                break;
            case R.id.corte:
                i = new Intent(this,ActivityCorte.class);
                i.putExtra("TIPO", "LIDER");;
                startActivity(i);
                break;
            default:
                break;
        }
    }
}
