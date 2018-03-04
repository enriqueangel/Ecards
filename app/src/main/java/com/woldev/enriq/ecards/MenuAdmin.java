package com.woldev.enriq.ecards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MenuAdmin extends AppCompatActivity implements View.OnClickListener{

    private CardView CrearTarjeta, CrearReunion,Usuarios, Dashboard;
    private VariablesGlobales globalVariable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        globalVariable = (VariablesGlobales) getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(toolbar);

        CrearTarjeta = (CardView) findViewById(R.id.crear_tarjeta);
        CrearReunion = (CardView) findViewById(R.id.crear_reunion);
        Usuarios = (CardView) findViewById(R.id.usuarios);
        Dashboard = (CardView) findViewById(R.id.dashboard);

        CrearTarjeta.setOnClickListener(this);
        CrearReunion.setOnClickListener(this);
        Usuarios.setOnClickListener(this);
        Dashboard.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.crear_tarjeta:
                i = new Intent(MenuAdmin.this, Crear_Tarjeta.class);
                i.putExtra("TIPO", "ADMIN");
                startActivity(i);
                break;
            case R.id.crear_reunion:
                i = new Intent(MenuAdmin.this, CrearReunion.class);
                i.putExtra("TIPO", "ADMIN");
                startActivity(i);
                break;
            case R.id.usuarios:
                i = new Intent(MenuAdmin.this, ListaUsuarios.class);
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
