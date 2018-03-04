package com.woldev.enriq.ecards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by enriq on 1/02/2018.
 */

public class ElementoListaAdapter extends BaseAdapter {
    private ArrayList<ElementoLista> items;
    private Context context;

    public ElementoListaAdapter(Context context, ArrayList<ElementoLista> items) {
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
        ElementoLista tarea = items.get(i);
        TextView tipo_tarea = (TextView) view.findViewById(R.id.tipo_tarea);
        tipo_tarea.setText(tarea.getNombre());
        return view;
    }
}
