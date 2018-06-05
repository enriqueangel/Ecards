package com.woldev.enriq.ecards;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ActivityMenuLider extends AppCompatActivity implements View.OnClickListener {

    private VariablesGlobales globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lider);

        globalVariable = (VariablesGlobales) getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(toolbar);

        CardView crearTarjeta = (CardView) findViewById(R.id.crear_tarjeta);
        CardView crearReunion = (CardView) findViewById(R.id.crear_reunion);
        CardView crearTester = (CardView) findViewById(R.id.crear_tester);
        CardView crearTipoTarea = (CardView) findViewById(R.id.crear_tipo_tarea);
        CardView corte = (CardView) findViewById(R.id.corte);
        CardView rama = (CardView) findViewById(R.id.rama);
        CardView usuarios = (CardView) findViewById(R.id.usuarios);
        CardView dashboard = (CardView) findViewById(R.id.dashboard);

        crearTarjeta.setOnClickListener(this);
        crearReunion.setOnClickListener(this);
        crearTester.setOnClickListener(this);
        crearTipoTarea.setOnClickListener(this);
        corte.setOnClickListener(this);
        rama.setOnClickListener(this);
        usuarios.setOnClickListener(this);
        dashboard.setOnClickListener(this);

        if (globalVariable.getCantRoles() == 1){
            dashboard.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.crear_tarjeta:
                i = new Intent(ActivityMenuLider.this, ActivityCrearTarjeta.class);
                i.putExtra("TIPO", "LIDER");
                startActivity(i);
                break;
            case R.id.crear_reunion:
                i = new Intent(ActivityMenuLider.this,ActivityCrearReunion.class);
                i.putExtra("TIPO", "LIDER");
                startActivity(i);
                break;
            case R.id.crear_tester:
                i = new Intent(ActivityMenuLider.this,ActivityCrearTester.class);
                i.putExtra("TIPO", "LIDER");
                startActivity(i);
                break;
            case R.id.crear_tipo_tarea:
                i = new Intent(this, ActivityTiposTareas.class);
                startActivity(i);
                break;
            case R.id.corte:
                i = new Intent(ActivityMenuLider.this,ActivityCorte.class);
                i.putExtra("TIPO", "LIDER");;
                startActivity(i);
                break;
            case R.id.rama:
                i = new Intent(ActivityMenuLider.this, ActivityRama.class);
                startActivity(i);
                break;
            case R.id.usuarios:
                i = new Intent(ActivityMenuLider.this, ActivityListaUsuarios.class);
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
