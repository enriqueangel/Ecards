package com.woldev.enriq.ecards;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ValentinaR on 22/01/2018.
 */

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.UsuarioViewHolder>{
    private List<Usuario> items;
    private Context context;

    class UsuarioViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imagen;
        TextView nombre, horasl, horast,hl,ht;
        JSONObject DATOS;

        UsuarioViewHolder(View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.imageV);
            nombre = itemView.findViewById(R.id.nombre);
            horasl = itemView.findViewById(R.id.campo_horas_laborales);
            horast = itemView.findViewById(R.id.campo_horas_trabajadas);
            hl = itemView.findViewById(R.id.horas_laborales);
            ht = itemView.findViewById(R.id.horas_trabajadas);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    intent = new Intent(v.getContext(), ActivityInformacionUsuario.class);
                    intent.putExtra( "DATOS", DATOS.toString());
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    AdapterUsuarios(List<Usuario> items, Context context){
        this.context = context;
        this.items = items;
    }

    @Override
    public UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usuario_card, parent, false);
        return new AdapterUsuarios.UsuarioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UsuarioViewHolder holder, int position) {
        holder.nombre.setText(items.get(position).getNombre());
        Picasso.with(context).load(items.get(position).getFoto()).into(holder.imagen);
        holder.horasl.setText(items.get(position).getHorasl());
        holder.horast.setText(items.get(position).getHorast());
        holder.hl.setText(items.get(position).getHl());
        holder.ht.setText(items.get(position).getHt());
        holder.DATOS = items.get(position).getDATOS();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}


