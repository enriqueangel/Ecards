package com.example.enriq.ecards;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Laura on 14/12/2017.
 */

public class ViewHolder_reunion extends RecyclerView.ViewHolder {

    TextView titulo,fecha,lugar,hora;

    public ViewHolder_reunion(View itemView) {
        super(itemView);

        titulo = (TextView) itemView.findViewById(R.id.EDTtitulo);
        fecha = (TextView)  itemView.findViewById(R.id.EDTfecha);
        lugar = (TextView) itemView.findViewById(R.id.EDTlugar);
        hora = (TextView) itemView.findViewById(R.id.hora);
    }
}
