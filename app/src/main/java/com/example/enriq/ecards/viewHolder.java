package com.example.enriq.ecards;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;


/**
 * Created by Laura on 7/12/2017.
 */

public class viewHolder extends RecyclerView.ViewHolder {

    TextView titulo,tipo,fecha_e,tiempo_r,version;


    public viewHolder(View itemView) {
        super(itemView);

        titulo = (TextView) itemView.findViewById(R.id.titulo);
        tipo = (TextView) itemView.findViewById(R.id.tipo);
        fecha_e = (TextView) itemView.findViewById(R.id.fecha_e);
        tiempo_r = (TextView) itemView.findViewById(R.id.tiempo_r);
        version = (TextView) itemView.findViewById(R.id.vr);

    }
}
