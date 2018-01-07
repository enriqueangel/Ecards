package com.example.enriq.ecards;

import android.content.Intent;
import android.os.Bundle;
import com.getbase.floatingactionbutton.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Laura on 7/12/2017.
 */

/* contenedor es el id del recyclerview en el layout de cards*/

public class Card extends AppCompatActivity {
    RecyclerView contenedor;

    FloatingActionButton clickperf;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards);

        clickperf = (FloatingActionButton) findViewById(R.id.perfil);


        ArrayList<Fuente> lista = new ArrayList<Fuente>();
        lista.add(new Fuente("Creación de vista","Frontend","32 Horas","10 Horas","1",R.drawable.card_green,false));
        lista.add(new Fuente("Creación de vista","Frontend","32 Horas","10 Horas","1",R.drawable.card_indigo,true));
        lista.add(new Fuente("Creación de vista","Frontend","32 Horas","10 Horas","1",R.drawable.card_red,false));
        lista.add(new Fuente("Creación de vista","Frontend","32 Horas","10 Horas","1",R.drawable.card_white,false));
        lista.add(new Fuente("Creación de vista","Frontend","32 Horas","10 Horas","1",R.drawable.card_yellow,false));



        contenedor = (RecyclerView) findViewById(R.id.contenedor);
        contenedor.setHasFixedSize(true);// no va a presentar variables en cuanto al tamaño
        RelativeLayout layout = new RelativeLayout(getApplicationContext());
        layout.setVerticalGravity(RelativeLayout.CENTER_VERTICAL);

        //INDICO CUAL TARJETA QUIERO MOSTRAR, PENDIENTE:PROGRAMAR LA ESCOGENCIA DE LA TARJETA
        contenedor.setAdapter(new Adaptador(lista));
        //contenedor.setAdapter(new Adaptador_reunion(lista2));
        // contenedor.setAdapter(new Adaptador_yellow(lista3));
        //contenedor.setAdapter(new Adaptador_red(lista4));

        contenedor.setLayoutManager(new LinearLayoutManager(this));



        clickperf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Card.this, editperfil.class);
                startActivity(intent);
            }
        });


    }



}


