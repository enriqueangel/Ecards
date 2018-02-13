package com.example.enriq.ecards;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Laura on 8/02/2018.
 */

public class Adaptador_Notificaciones extends RecyclerView.Adapter<Adaptador_Notificaciones.NotViewHolder>{

    List<Fuente_Notificaciones> ListaObjeto;



    class NotViewHolder extends RecyclerView.ViewHolder {
        TextView Titulo, Descripcion, Fecha, Hora;
        JSONObject DATOS;

        public NotViewHolder(View itemView) {
            super(itemView);
            TextView Titulo = (TextView) itemView.findViewById(R.id.Titulo);
            TextView Descripcion = (TextView) itemView.findViewById(R.id.Descripcion);
            TextView Fecha = (TextView) itemView.findViewById(R.id.Fecha);
            TextView Hora = (TextView) itemView.findViewById(R.id.Hora);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    intent = new Intent(v.getContext(), Tarjeta.class);
                    intent.putExtra("DATOS", DATOS.toString());
                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public NotViewHolder onCreateViewHolder(ViewGroup viewGroup,int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_drawer, viewGroup, false);
        return new NotViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NotViewHolder viewHolder,int i) {
        viewHolder.Titulo.setText(ListaObjeto.get(i).getTitulo());
        viewHolder.Descripcion.setText(ListaObjeto.get(i).getDescripcion());
        viewHolder.Fecha.setText(ListaObjeto.get(i).getFecha());
        viewHolder.Hora.setText(ListaObjeto.get(i).getHora());
    }

    @Override
    public int getItemCount() {
        return ListaObjeto.size();
    }

   }
