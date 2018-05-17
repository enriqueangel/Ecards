package com.woldev.enriq.ecards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laura on 8/02/2018.
 */

public class AdapterNotificaciones extends BaseAdapter {


    List<Notificacion> ListaNotificaciones;
    private Context context;
    //JSONObject DATOS;

    public AdapterNotificaciones(Context context, ArrayList<Notificacion> listaNotificaciones) {
        this.ListaNotificaciones = listaNotificaciones;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ListaNotificaciones.size();
    }

    @Override
    public Object getItem(int i) {
        return ListaNotificaciones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.design_notificacion, null);
        }

        TextView Titulo = (TextView) view.findViewById(R.id.Titulo);
        TextView Descripcion = (TextView) view.findViewById(R.id.Descripcion);
        TextView Fecha = (TextView) view.findViewById(R.id.Fecha);
        TextView Hora = (TextView) view.findViewById(R.id.Hora);

        Titulo.setText(ListaNotificaciones.get(i).getTitulo());
        Descripcion.setText(ListaNotificaciones.get(i).getDescripcion());
        Fecha.setText(ListaNotificaciones.get(i).getFecha());
        Hora.setText(ListaNotificaciones.get(i).getHora());

        return view;
    }
}