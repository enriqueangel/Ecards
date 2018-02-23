package com.example.enriq.ecards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import java.util.ArrayList;

public class TarjetasFragment extends Fragment {

    RecyclerView contenedor;
    ArrayList<Fuente> listaTarjetas = new ArrayList<Fuente>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tarjetas, container, false);

        JSONObject row = null;
        listaTarjetas.add(new Fuente("asdfasdf","asdfasdf","asdfasdf","asdfasd", "adfasdf", R.drawable.card_indigo,false, row));

        contenedor = (RecyclerView) view.findViewById(R.id.contenedor);
        contenedor.setHasFixedSize(true);// no va a presentar variables en cuanto al tamaño
        RelativeLayout layout = new RelativeLayout(getActivity().getApplicationContext());
        layout.setVerticalGravity(RelativeLayout.CENTER_VERTICAL);

        //INDICO CUAL TARJETA QUIERO MOSTRAR, PENDIENTE:PROGRAMAR LA ESCOGENCIA DE LA TARJETA
        contenedor.setAdapter(new Adaptador(listaTarjetas));

        contenedor.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
