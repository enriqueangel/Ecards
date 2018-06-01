package com.woldev.enriq.ecards;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by enriq on 1/02/2018.
 */

public class AdapterElementoListaButton extends BaseAdapter {
    private ArrayList<ElementoLista> items;
    private Context context;

    public AdapterElementoListaButton(Context context, ArrayList<ElementoLista> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  inflater.inflate(R.layout.item_elemento_lista, null);
        }
        final ElementoLista tarea = items.get(i);
        TextView tipo_tarea = (TextView) view.findViewById(R.id.tipo_tarea);
        tipo_tarea.setText(tarea.getNombre());

        try {
            JSONObject datos = items.get(i).getDatos();
            final String clasificacion = datos.getString("clasificacion_tarea");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageButton boton = (ImageButton) view.findViewById(R.id.editar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2;
                i2 = new Intent(context, ActivityEditarTipoTarea.class);
                i2.putExtra("IDTAREA",tarea.getId());
                context.startActivity(i2);
            }
        });

        return view;
    }

}
