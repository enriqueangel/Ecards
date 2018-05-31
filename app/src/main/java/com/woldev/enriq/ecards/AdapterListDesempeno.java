package com.woldev.enriq.ecards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by enriq on 9/04/2018.
 */

public class AdapterListDesempeno extends BaseAdapter {
    private ArrayList<Desempeno> items;
    private Context context;

    public AdapterListDesempeno(Context context, ArrayList<Desempeno> items) {
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
            view =  inflater.inflate(R.layout.list_item_desempeno, null);
        }

        Desempeno item = items.get(i);

        TextView txtListChild = (TextView) view.findViewById(R.id.ListItemdesempeno);
        TextView HorasDesempeno = (TextView) view.findViewById(R.id.horasdesempeno);
        TextView Descripcion = (TextView) view.findViewById(R.id.descripcion);

        txtListChild.setText(item.getNombre());
        HorasDesempeno.setText(item.getHora());
        Descripcion.setText(item.getDescripcion());

        return view;
    }
}
