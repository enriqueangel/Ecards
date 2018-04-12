package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by enriq on 1/02/2018.
 */

public class ElementoListButtonAdapter extends BaseAdapter {
    private ArrayList<ElementoLista> items;
    private Context context;

    public ElementoListButtonAdapter(Context context, ArrayList<ElementoLista> items) {
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

            ImageButton boton = (ImageButton) view.findViewById(R.id.editar);
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    crearDialogEditar(tarea.getNombre(), view, clasificacion);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void crearDialogEditar(String nombre, final View view, String clasificacion){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final AlertDialog.Builder agregarTarea = new AlertDialog.Builder(view.getContext(), R.style.MyDialogTheme);
        View mView = inflater.inflate(R.layout.dialog_crear_tarea, null);
        agregarTarea.setView(mView);
        agregarTarea.setTitle("Modificar tipo tarea");

        EditText campoNombre = mView.findViewById(R.id.nombre);
        campoNombre.setText(nombre);

        if (clasificacion.equals("estudio")) {
            RadioButton estudio = (RadioButton) mView.findViewById(R.id.estudio);
            estudio.setChecked(true);
        } else {
            RadioButton trabajo = (RadioButton) mView.findViewById(R.id.trabajo);
            trabajo.setChecked(true);
        }

        agregarTarea.setPositiveButton("Modificar", null);

        agregarTarea.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = agregarTarea.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), "Tipo tarea modificado", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        dialog.show();
    }
}
