package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by enriq on 9/04/2018.
 */

public class ElementoListAdapter  extends BaseAdapter {
    private ArrayList<ElementoLista> items;
    private Context context;

    public ElementoListAdapter(Context context, ArrayList<ElementoLista> items) {
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
            view =  inflater.inflate(R.layout.list_item, null);
        }
        final ElementoLista rama = items.get(i);
        TextView campo_rama = (TextView) view.findViewById(R.id.tipo_tarea);
        campo_rama.setText(rama.getNombre());

        return view;
    }
}
