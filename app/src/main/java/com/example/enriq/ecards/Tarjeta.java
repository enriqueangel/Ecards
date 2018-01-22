package com.example.enriq.ecards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class Tarjeta extends AppCompatActivity implements View.OnClickListener {

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
