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
        final ElementoLista tarea = items.get(i);
        TextView tipo_tarea = (TextView) view.findViewById(R.id.tipo_tarea);
        tipo_tarea.setText(tarea.getNombre());

        ImageButton boton = (ImageButton) view.findViewById(R.id.editar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearDialogEditar(tarea.getNombre(), view);
            }
        });
        return view;
    }

    private void crearDialogEditar(String nombre, final View view){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final AlertDialog.Builder agregarTarea = new AlertDialog.Builder(view.getContext(), R.style.MyDialogTheme);
        View mView = inflater.inflate(R.layout.dialog_editar, null);
        agregarTarea.setView(mView);
        agregarTarea.setTitle("Modificar tipo tarea");

        EditText campoNombre = mView.findViewById(R.id.nombre);
        campoNombre.setText(nombre);

        agregarTarea.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(view.getContext(), "Tipo tarea modificado", Toast.LENGTH_LONG).show();
            }
        });

        agregarTarea.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = agregarTarea.create();
        dialog.show();
    }
}
