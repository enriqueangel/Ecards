package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

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

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageButton boton = (ImageButton) view.findViewById(R.id.editar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2;
                i2 = new Intent(context, ActivityCrearTipoTarea.class);
                i2.putExtra("IDTAREA",tarea.getId());
                context.startActivity(i2);
            }
        });

        return view;
    }

}
