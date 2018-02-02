package com.example.enriq.ecards;

import android.app.Activity;
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

public class TipoTareaAdapter extends BaseAdapter {
    private ArrayList<TipoTarea> items;
    private Context context;

    public TipoTareaAdapter(Context context, ArrayList<TipoTarea> items) {
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
            view =  inflater.inflate(R.layout.item_tipo_tarea, null);
        }
        TipoTarea tarea = items.get(i);
        TextView tipo_tarea = (TextView) view.findViewById(R.id.tipo_tarea);
        tipo_tarea.setText(tarea.getNombre());
        return view;
    }
}
