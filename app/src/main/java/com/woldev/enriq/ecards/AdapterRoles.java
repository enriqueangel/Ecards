package com.woldev.enriq.ecards;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by enriq on 5/01/2018.
 */

public class AdapterRoles extends RecyclerView.Adapter<AdapterRoles.RolViewHolder>{
    private List<Rol> items;

    class RolViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView texto;
        Rol roles;

        RolViewHolder(View v){
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imageCard);
            texto = (TextView) v.findViewById(R.id.textCard);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String rol = roles.getText();
                    Intent intent;
                    switch (rol){
                        case "Empleado":
                            intent = new Intent(v.getContext(), ActivityTarjetas.class);
                            v.getContext().startActivity(intent);
                            break;
                        case "Administrador":
                            intent = new Intent(v.getContext(), ActivityMenuAdmin.class);
                            v.getContext().startActivity(intent);
                            break;
                        case "Lider":
                            intent = new Intent(v.getContext(), ActivityMenuLider.class);
                            v.getContext().startActivity(intent);
                            break;
                        case "Super Usuario":
                            intent = new Intent(v.getContext(), ActivityMenuSuperU.class);
                            v.getContext().startActivity(intent);
                            break;
                        default:
                            break;
                    }
                    ((Activity)v.getContext()).finish();
                }
            });
        }
    }

    AdapterRoles(List<Rol> items){
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RolViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dashboard_card, viewGroup, false);
        return new RolViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RolViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImage());
        viewHolder.texto.setText(items.get(i).getText());
        viewHolder.roles = items.get(i);;
    }
}
