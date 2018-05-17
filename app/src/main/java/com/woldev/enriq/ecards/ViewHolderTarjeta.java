package com.woldev.enriq.ecards;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;


/**
 * Created by Laura on 7/12/2017.
 */

class ViewHolderTarjeta extends RecyclerView.ViewHolder {

    LinearLayout campoImagen;
    RelativeLayout contenedor;
    TextView titulo,tipo,fecha_e,tiempo_r,version,TituloCampo1,TituloCampo2;
    ImageView TarjetaColor;
    JSONObject DATOS;
    Boolean Activo;

    ViewHolderTarjeta(View itemView) {
        super(itemView);

        titulo = (TextView) itemView.findViewById(R.id.EDTtitulo);
        tipo = (TextView) itemView.findViewById(R.id.TXVtipo);
        fecha_e = (TextView) itemView.findViewById(R.id.fecha_e);
        tiempo_r = (TextView) itemView.findViewById(R.id.tiempo_r);
        version = (TextView) itemView.findViewById(R.id.vr);
        TarjetaColor = (ImageView) itemView.findViewById(R.id.imagen);

        TituloCampo1 = (TextView) itemView.findViewById(R.id.titulo_tiempo);
        TituloCampo2 = (TextView) itemView.findViewById(R.id.titulo_fecha);

        campoImagen = (LinearLayout) itemView.findViewById(R.id.campoImagen);
        contenedor = (RelativeLayout) itemView.findViewById(R.id.contenedor);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Activo){
                    Intent intent;
                    intent = new Intent(v.getContext(), ActivityVerTarjeta.class);
                    intent.putExtra( "DATOS", DATOS.toString());
                    v.getContext().startActivity(intent);
                }
            }
        });
    }
}
