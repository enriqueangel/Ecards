package com.woldev.enriq.ecards;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laura on 7/12/2017.
 */

public class AdapterTarjetas extends RecyclerView.Adapter<ViewHolderTarjeta> {

    List<Tarjeta> ListaObjeto;

    public AdapterTarjetas(ArrayList<Tarjeta> listaObjeto) {
        ListaObjeto = listaObjeto;
    }

    @Override
    public ViewHolderTarjeta onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_design, parent, false);
        return new ViewHolderTarjeta(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolderTarjeta holder, int position) {
        holder.titulo.setText(ListaObjeto.get(position).getTitulo());
        holder.tipo.setText(ListaObjeto.get(position).getTipo());
        holder.fecha_e.setText(ListaObjeto.get(position).getTiempo_e());
        holder.tiempo_r.setText(ListaObjeto.get(position).getTiempo_r());
        holder.version.setText(ListaObjeto.get(position).getVersion());
        holder.TarjetaColor.setImageResource(ListaObjeto.get(position).getColor());
        holder.DATOS = ListaObjeto.get(position).getDATOS();
        Boolean activo = ListaObjeto.get(position).getActiva();
        holder.Activo = activo;

        if (ListaObjeto.get(position).getReunion()) {
            holder.TituloCampo1.setText("Lugar");
            holder.TituloCampo2.setText("Fecha");
        }

        if (!activo){
            holder.TarjetaColor.setImageResource(R.drawable.ic_card_grey);
            holder.contenedor.setBackgroundResource(R.color.grey_light);
            holder.campoImagen.setBackgroundResource(R.color.grey_dark);
        }
    }

    @Override
    public int getItemCount() {
        return ListaObjeto.size();
    }


}