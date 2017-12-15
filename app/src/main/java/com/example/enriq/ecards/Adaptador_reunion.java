package com.example.enriq.ecards;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Laura on 14/12/2017.
 */

public class Adaptador_reunion extends RecyclerView.Adapter<ViewHolder_reunion>{

    List<Fuente_reunion> ListaObjeto;

    public Adaptador_reunion(List<Fuente_reunion> listaObjeto) {
        ListaObjeto = listaObjeto;
    }


    @Override
    public ViewHolder_reunion onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_reunion, parent, false);
        return new ViewHolder_reunion(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder_reunion holder, int position) {
        holder.titulo.setText(ListaObjeto.get(position).getTitulo());
        holder.fecha.setText(ListaObjeto.get(position).getFecha());
        holder.lugar.setText(ListaObjeto.get(position).getLugar());
        holder.hora.setText(ListaObjeto.get(position).getHora());
    }

    @Override
    public int getItemCount() {
        return ListaObjeto.size();
    }
}
