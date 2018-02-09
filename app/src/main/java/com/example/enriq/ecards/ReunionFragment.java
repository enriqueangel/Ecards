package com.example.enriq.ecards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ReunionFragment extends Fragment {

    JSONObject DATOS;
    TextView Fecha, Hora , Lugar , Descripcion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v =  inflater.inflate(R.layout.fragment_reunion, container, false);

        Fecha = (TextView) v.findViewById(R.id.TXVfecha);
        Hora = (TextView) v.findViewById(R.id.TXVHora);
        Lugar = (TextView) v.findViewById(R.id.TXVLugar);
        Descripcion = (TextView) v.findViewById(R.id.TXVdescripcion);


        try {
            DATOS = new JSONObject(getArguments().getString("DATOS"));
            Fecha.setText(DATOS.getString("fecha"));
            Hora.setText(DATOS.getString("hora"));
            Lugar.setText(DATOS.getString("lugar"));
            Descripcion.setText(DATOS.getString("descripcion"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }
}
