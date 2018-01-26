package com.example.enriq.ecards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ListaUsuarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_usuarios);

        RecyclerView contenedor = (RecyclerView) findViewById(R.id.contenedor);
        contenedor.setHasFixedSize(true);

        LinearLayoutManager linear =  new LinearLayoutManager(getApplicationContext());
        linear.setOrientation(LinearLayoutManager.VERTICAL);

        ArrayList<Usuario> items = new ArrayList<Usuario>();

        items.add(new Usuario(R.drawable.noimg, "Enrique Angel", "Horas Laborales:", "Horas Trabajadas:","15","30"));
        items.add(new Usuario(R.drawable.noimg, "Valentina Rojas", "Horas Laborales:", "Horas Trabajadas:","15","40"));
        items.add(new Usuario(R.drawable.noimg, "Ronal Gonzales", "Horas Laborales:", "Horas Trabajadas:","13","50"));
        items.add(new Usuario(R.drawable.noimg, "Laura Gonzales", "Horas Laborales:", "Horas Trabajadas:","13","60"));

        contenedor.setAdapter(new UsuarioAdapter(items));
        contenedor.setLayoutManager(linear);
    }
}
