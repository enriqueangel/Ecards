package com.example.enriq.ecards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Laura on 7/12/2017.
 */

/* contenedor es el id del recyclerview en el layout de cards*/

public class Card extends AppCompatActivity {
    RecyclerView contenedor;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards);

        ArrayList<Fuente> lista = new ArrayList<Fuente>();
        lista.add(new Fuente("Creación de vista","Frontend","32 Horas","10 Horas","Blanco"));

        contenedor = (RecyclerView) findViewById(R.id.contenedor);
        contenedor.setHasFixedSize(true);// no va a presentar variables en cuanto al tamaño
        RelativeLayout layout = new RelativeLayout(getApplicationContext());
        layout.setVerticalGravity(RelativeLayout.CENTER_VERTICAL);
        contenedor.setAdapter(new Adaptador(lista));
        contenedor.setLayoutManager(new LinearLayoutManager(this));

    }

}


