package com.woldev.enriq.ecards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MenuSuperU extends AppCompatActivity implements View.OnClickListener  {

    private CardView crearTarjeta, crearReunion, crearTester, crearTipoTarea, proyectos, UserNuevos, rama, usuarios, corte, dashboard;
    private VariablesGlobales globalVariable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_super_u);

        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(toolbar);

        globalVariable = (VariablesGlobales) getApplicationContext();

        crearTarjeta = (CardView) findViewById(R.id.crear_tarjeta);
        crearReunion = (CardView) findViewById(R.id.crear_reunion);
        crearTester = (CardView) findViewById(R.id.crear_tester);
        crearTipoTarea = (CardView) findViewById(R.id.crear_tipo_tarea);
        proyectos = (CardView) findViewById(R.id.proyectos);
        UserNuevos = (CardView) findViewById(R.id.usuarios_nuevos);
        rama = (CardView) findViewById(R.id.rama);
        usuarios = (CardView) findViewById(R.id.usuarios);
        corte = (CardView) findViewById(R.id.corte);
        dashboard = (CardView) findViewById(R.id.dashboard);

        crearTarjeta.setOnClickListener(this);
        crearReunion.setOnClickListener(this);
        crearTester.setOnClickListener(this);
        crearTipoTarea.setOnClickListener(this);
        proyectos.setOnClickListener(this);
        UserNuevos.setOnClickListener(this);
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
                i = new Intent(MenuSuperU.this, Crear_Tarjeta.class);
                i.putExtra("TIPO", "SUPERUSER");
                startActivity(i);
                break;
            case R.id.crear_reunion:
                i = new Intent(MenuSuperU.this,CrearReunion.class);
                i.putExtra("TIPO", "SUPERUSER");
                startActivity(i);
                break;
            case R.id.crear_tester:
                i = new Intent(MenuSuperU.this,Crear_Tester.class);
                i.putExtra("TIPO", "SUPERUSER");
                startActivity(i);
                break;
            case R.id.crear_tipo_tarea:
                i = new Intent(this, TiposTareas.class);
                startActivity(i);
                break;
            case R.id.proyectos:
                i = new Intent(this, Proyectos.class);
                startActivity(i);
                break;
            case R.id.usuarios_nuevos:
                i = new Intent(this, UsuariosNuevos.class);
                startActivity(i);
                break;
            case R.id.rama:
                i = new Intent(this, Ramas.class);
                startActivity(i);
                break;
            case R.id.usuarios:
                i = new Intent(this, ExpandableUsuarioActivity.class);
                startActivity(i);
                break;
            case R.id.corte:
                i = new Intent(this,Corte.class);
                i.putExtra("TIPO", "SUPERUSER");
                startActivity(i);
                break;
            case R.id.dashboard:
                i = new Intent(this, Dashboard.class);
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
