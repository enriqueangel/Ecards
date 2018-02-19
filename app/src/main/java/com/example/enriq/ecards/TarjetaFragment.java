package com.example.enriq.ecards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TarjetaFragment extends Fragment {

    JSONObject DATOS;
    TextView Fecha_Entrega , Proyecto , TipoTarea, TiempoEstimado, TiempoRealizado, LinkAyuda , Descripcion;
    RelativeLayout TILTipoTarea,TILTiempoEstimado,TILLinkAyuda;

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

        TILTipoTarea = (RelativeLayout) v.findViewById(R.id.TILTipoTarea);
        TILTiempoEstimado = (RelativeLayout) v.findViewById(R.id.TILTiempoEstimado);
        TILLinkAyuda = (RelativeLayout) v.findViewById(R.id.TILLinkAyuda);


        try {
            DATOS = new JSONObject(getArguments().getString("DATOS"));

            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            // el que formatea
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

            String fechaTemp1 = DATOS.getString("fecha_entrega") ;

            Date date = null;
            try {
                date = parseador.parse(fechaTemp1);
                Fecha_Entrega.setText(formateador.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }



            JSONObject ProyectoTEMP = DATOS.getJSONObject("proyecto");
            Proyecto.setText(ProyectoTEMP.getString("nombre"));

            LinkAyuda.setText(DATOS.getString("link_recurso"));
            Descripcion.setText(DATOS.getString("descripcion"));

            JSONObject TipoTareaTEMP = DATOS.getJSONObject("tipotarea");
            TipoTarea.setText(TipoTareaTEMP.getString("tipo"));



            if (DATOS.getString("link_recurso") == null){
                TILLinkAyuda.setVisibility(View.GONE);
            }

            if (DATOS.getString("color").equals("verde")){

                TILTipoTarea.setVisibility(View.GONE);
                TILTiempoEstimado.setVisibility(View.GONE);

            }else{
                JSONObject TipoTareaTemp = DATOS.getJSONObject("tipotarea");
                TipoTarea.setText(TipoTareaTemp.getString("nombre"));
                TiempoEstimado.setText(DATOS.getString("tiempo_estimado"));
                TiempoRealizado.setText(DATOS.getString("tiempo_trabajado"));
                if (DATOS.getString("tiempo_estimado").equals("00:00")){
                    TILTiempoEstimado.setVisibility(View.GONE);
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }
}
