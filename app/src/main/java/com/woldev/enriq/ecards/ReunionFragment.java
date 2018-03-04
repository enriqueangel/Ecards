package com.woldev.enriq.ecards;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            // el que formatea
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

            String fechaTemp1 = DATOS.getString("fecha") ;

            Date date = null;
            try {
                date = parseador.parse(fechaTemp1);
                Fecha.setText(formateador.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }



            Hora.setText(DATOS.getString("hora"));
            Lugar.setText(DATOS.getString("lugar"));
            Descripcion.setText(DATOS.getString("descripcion"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }
}
