package com.example.enriq.ecards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MenuLider extends AppCompatActivity implements View.OnClickListener {

    private CardView crearTarjeta, crearReunion, crearTester, crearTipoTarea, corte, rama, usuarios, dashboard;
    private VariablesGlobales globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lider);

        globalVariable = (VariablesGlobales) getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(toolbar);

        crearTarjeta = (CardView) findViewById(R.id.crear_tarjeta);
        crearReunion = (CardView) findViewById(R.id.crear_reunion);
        crearTester = (CardView) findViewById(R.id.crear_tester);
        crearTipoTarea = (CardView) findViewById(R.id.crear_tipo_tarea);
        corte = (CardView) findViewById(R.id.corte);
        rama = (CardView) findViewById(R.id.rama);
        usuarios = (CardView) findViewById(R.id.usuarios);
        dashboard = (CardView) findViewById(R.id.dashboard);

        crearTarjeta.setOnClickListener(this);
        crearReunion.setOnClickListener(this);
        crearTester.setOnClickListener(this);
        crearTipoTarea.setOnClickListener(this);
        corte.setOnClickListener(this);
        rama.setOnClickListener(this);
        usuarios.setOnClickListener(this);
        dashboard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.crear_tarjeta:
                break;
            case R.id.crear_reunion:
                i = new Intent(MenuLider.this,CrearReunion.class);
                startActivity(i);
                break;
            case R.id.crear_tester:
                break;
            case R.id.crear_tipo_tarea:
                break;
            case R.id.corte:
                break;
            case R.id.rama:
                break;
            case R.id.usuarios:
                break;
            case R.id.dashboard:
                i = new Intent(this, Dashboard.class);
                String areasTEmp = globalVariable.getAreas();
                i.putExtra( "Areas", areasTEmp);
                startActivity(i);
                break;
            default:
                break;
        }

        finish();
    }
}
