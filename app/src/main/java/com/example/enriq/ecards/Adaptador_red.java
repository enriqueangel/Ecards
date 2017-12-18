package com.example.enriq.ecards;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Laura on 7/12/2017.
 */

public class Adaptador_red extends RecyclerView.Adapter<viewHolder> {

    List<Fuente> ListaObjeto;

    public Adaptador_red(List<Fuente> listaObjeto) {
        ListaObjeto = listaObjeto;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_red, parent, false);
        return new viewHolder(vista);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.titulo.setText(ListaObjeto.get(position).getTitulo());
        holder.tipo.setText(ListaObjeto.get(position).getTipo());
        holder.fecha_e.setText(ListaObjeto.get(position).getTiempo_e());
        holder.tiempo_r.setText(ListaObjeto.get(position).getTiempo_r());
        holder.version.setText(ListaObjeto.get(position).getVersion());
    }

    @Override
    public int getItemCount() {
        return ListaObjeto.size();
    }
}
