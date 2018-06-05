package com.woldev.enriq.ecards;

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
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentInformacionTester extends Fragment {

    JSONObject DATOS;
    TextView Encargado,Fecha_Entrega,Proyecto,Tiempo_Realizado,Tiempo_Estimado,Link,Descripcion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tester, container, false);

        Fecha_Entrega = (TextView) v.findViewById(R.id.TXVfecha_entrega);
        Tiempo_Realizado = (TextView) v.findViewById(R.id.TXVtiempo_desarrollo);

        Encargado = (TextView) v.findViewById(R.id.TXVencargado);
        Proyecto = (TextView) v.findViewById(R.id.TXVproyecto);
        Tiempo_Estimado = (TextView) v.findViewById(R.id.TXVtiempo_estimado);
        Link = (TextView) v.findViewById(R.id.TXVlink);
        Descripcion = (TextView) v.findViewById(R.id.TXVdescripcion);

        try {
            DATOS = new JSONObject(getArguments().getString("DATOS"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String TiempoRealizadoTemp = DATOS.getString("tiempo_trabajado");
            Tiempo_Realizado.setText(TiempoRealizadoTemp);


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

            JSONObject Tarjeta = DATOS.getJSONObject("tarjeta");
            JSONObject Responsable = Tarjeta.getJSONObject("responsable");
            String ResponsableMostrarTEmp = Responsable.getString("nombres")+" "+Responsable.getString("apellidos");
            Encargado.setText(ResponsableMostrarTEmp);
            String TiempoEstimadoTemp = Tarjeta.getString("tiempo_estimado");
            Tiempo_Estimado.setText(TiempoEstimadoTemp);

            String descripcionTemp  = Tarjeta.getString("descripcion");
            Descripcion.setText(descripcionTemp);

            JSONObject ProyectoTEmp = Tarjeta.getJSONObject("proyecto");
            String ProyectoNombreTEmp = ProyectoTEmp.getString("nombre");
            Proyecto.setText(ProyectoNombreTEmp);

            String LinkTemp = Tarjeta.getString("link_evidencia");
            Link.setText(LinkTemp);

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return  v;
    }
}
