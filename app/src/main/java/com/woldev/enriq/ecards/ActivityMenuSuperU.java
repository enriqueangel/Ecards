package com.woldev.enriq.ecards;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ActivityMenuSuperU extends AppCompatActivity implements View.OnClickListener  {

    private VariablesGlobales globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_super_u);

        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(toolbar);

        globalVariable = (VariablesGlobales) getApplicationContext();

        CardView crearTarjeta = (CardView) findViewById(R.id.crear_tarjeta);
        CardView crearReunion = (CardView) findViewById(R.id.crear_reunion);
        CardView crearTester = (CardView) findViewById(R.id.crear_tester);
        CardView crearTipoTarea = (CardView) findViewById(R.id.crear_tipo_tarea);
        CardView proyectos = (CardView) findViewById(R.id.proyectos);
        CardView userNuevos = (CardView) findViewById(R.id.usuarios_nuevos);
        CardView rama = (CardView) findViewById(R.id.rama);
        CardView usuarios = (CardView) findViewById(R.id.usuarios);
        CardView corte = (CardView) findViewById(R.id.corte);
        CardView dashboard = (CardView) findViewById(R.id.dashboard);

        crearTarjeta.setOnClickListener(this);
        crearReunion.setOnClickListener(this);
        crearTester.setOnClickListener(this);
        crearTipoTarea.setOnClickListener(this);
        proyectos.setOnClickListener(this);
        userNuevos.setOnClickListener(this);
        rama.setOnClickListener(this);
        usuarios.setOnClickListener(this);
        corte.setOnClickListener(this);
        dashboard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.crear_tarjeta:
                i = new Intent(ActivityMenuSuperU.this, ActivityCrearTarjeta.class);
                i.putExtra("TIPO", "SUPERUSER");
                startActivity(i);
                break;
            case R.id.crear_reunion:
                i = new Intent(ActivityMenuSuperU.this,ActivityCrearReunion.class);
                i.putExtra("TIPO", "SUPERUSER");
                startActivity(i);
                break;
            case R.id.crear_tester:
                i = new Intent(ActivityMenuSuperU.this,ActivityCrearTester.class);
                i.putExtra("TIPO", "SUPERUSER");
                startActivity(i);
                break;
            case R.id.crear_tipo_tarea:
                i = new Intent(this, ActivityTiposTareas.class);
                startActivity(i);
                break;
            case R.id.proyectos:
                i = new Intent(this, ActivityProyectos.class);
                startActivity(i);
                break;
            case R.id.usuarios_nuevos:
                i = new Intent(this, ActivityUsuariosNuevos.class);
                startActivity(i);
                break;
            case R.id.rama:
                i = new Intent(this, ActivityRamas.class);
                startActivity(i);
                break;
            case R.id.usuarios:
                i = new Intent(this, ExpandableSuperuActivity.class);
                startActivity(i);
                break;
            case R.id.corte:
                i = new Intent(this,ActivityCorte.class);
                i.putExtra("TIPO", "SUPERUSER");
                startActivity(i);
                break;
            case R.id.dashboard:
                i = new Intent(this, ActivityDashboard.class);
                String areasTEmp = globalVariable.getAreas();
                i.putExtra( "Areas", areasTEmp);
                startActivity(i);
                finish();
                break;
            default:
                break;
        }
    }
}
