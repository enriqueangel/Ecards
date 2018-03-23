package com.woldev.enriq.ecards;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laura on 7/12/2017.
 */

public class Adaptador extends RecyclerView.Adapter<viewHolder> {

    List<Fuente> ListaObjeto;

    public Adaptador(ArrayList<Fuente> listaObjeto) {
        ListaObjeto = listaObjeto;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_design, parent, false);
        return new viewHolder(vista);
    }



    @Override
    public void onBindViewHolder(viewHolder holder, int position) {


        holder.titulo.setText(ListaObjeto.get(position).getTitulo());
        holder.tipo.setText(ListaObjeto.get(position).getTipo());
        holder.fecha_e.setText(ListaObjeto.get(position).getTiempo_e());
        holder.tiempo_r.setText(ListaObjeto.get(position).getTiempo_r());
        holder.version.setText(ListaObjeto.get(position).getVersion());
        holder.TarjetaColor.setImageResource(ListaObjeto.get(position).getColor());
        holder.DATOS = ListaObjeto.get(position).getDATOS();


        if (ListaObjeto.get(position).getReunion()) {

            holder.TituloCampo1.setText("Lugar");
            holder.TituloCampo2.setText("Fecha");

        }else{



        };



    }

    @Override
    public int getItemCount() {
        return ListaObjeto.size();
    }


}