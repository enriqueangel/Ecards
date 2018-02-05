package com.example.enriq.ecards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class TarjetaFragment extends Fragment {

    JSONObject DATOS;
    TextView Fecha_Entrega , Proyecto , TipoTarea, TiempoEstimado, TiempoRealizado, LinkAyuda , Descripcion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_tarjeta, container, false);

        Fecha_Entrega = (TextView) v.findViewById(R.id.TXVfecha_entrega);
        Proyecto = (TextView) v.findViewById(R.id.TXVproyecto);
        TipoTarea = (TextView) v.findViewById(R.id.TXVtipo);
        TiempoEstimado = (TextView) v.findViewById(R.id.TXVtiempo_estimado);
        TiempoRealizado = (TextView) v.findViewById(R.id.TXVtiempo_desarrollo);
        LinkAyuda = (TextView) v.findViewById(R.id.TXVlink);
        Descripcion = (TextView) v.findViewById(R.id.TXVdescripcion);

        try {
            DATOS = new JSONObject(getArguments().getString("DATOS"));
            Fecha_Entrega.setText(DATOS.getString("fecha_entrega"));

            JSONObject ProyectoTEMP = DATOS.getJSONObject("proyecto");
            Proyecto.setText(ProyectoTEMP.getString("nombre"));

            JSONObject TipoTareaTemp = DATOS.getJSONObject("tipotarea");
            TipoTarea.setText(TipoTareaTemp.getString("nombre"));

            TiempoEstimado.setText(DATOS.getString("tiempo_estimado"));
            TiempoRealizado.setText(DATOS.getString("tiempo_trabajado"));
            LinkAyuda.setText(DATOS.getString("link_recurso"));
            Descripcion.setText(DATOS.getString("descripcion"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }
}
