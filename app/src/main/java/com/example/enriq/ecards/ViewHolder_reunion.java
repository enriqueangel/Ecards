package com.example.enriq.ecards;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Laura on 14/12/2017.
 */

public class ViewHolder_reunion extends RecyclerView.ViewHolder {

    TextView titulo,fecha,lugar,hora;

    public ViewHolder_reunion(View itemView) {
        super(itemView);

        titulo = (TextView) itemView.findViewById(R.id.titulo);
        fecha = (TextView)  itemView.findViewById(R.id.fecha);
        lugar = (TextView) itemView.findViewById(R.id.lugar);
        hora = (TextView) itemView.findViewById(R.id.hora);
    }
}
