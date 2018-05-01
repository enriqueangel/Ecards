package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        final View mView = inflater.inflate(R.layout.dialog_crear_tarea, null);
        agregarTarea.setView(mView);
        agregarTarea.setTitle("Modificar tipo tarea");

        final EditText Nombre = mView.findViewById(R.id.nombre);
        final TextInputLayout CampoNombre = mView.findViewById(R.id.CampoNombre);
        Nombre.setText(nombre);

        final RadioButton RadioButtonestudio = (RadioButton) mView.findViewById(R.id.estudio);

        if (clasificacion.equals("estudio")) {
            RadioButtonestudio.setChecked(true);
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

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
        View mView2 = inflater.inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
        final AlertDialog dialogCargando = mBuilder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        boolean enviar = true;

                        if  (!MetodosGlobales.validarCampoVacio(Nombre.getText().toString())){
                            CampoNombre.setError("Debe ingresar el nombre");
                            enviar = false;
                        }else{
                            CampoNombre.setError(null);
                        }

                        if(enviar){
                            dialogCargando.show();

                            String urltemp = view.getContext().getString(R.string.URLWS)+"tareas/editar";

                            final RequestQueue requestQueue = Volley.newRequestQueue(view.getContext().getApplicationContext());

                            Map<String, String> params = new HashMap<String, String>();

                            params.put("tipo", Nombre.getText().toString());

                            String Clasificacion = "";

                            if(RadioButtonestudio.isChecked()){
                                Clasificacion = "estudio";
                            }else{
                                Clasificacion = "trabajo";
                            }

                            params.put("clasificacion", Clasificacion);

                            JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urltemp, new JSONObject(params),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                String Respuesta = response.getString("respuesta");
                                                if (Respuesta.equals("si")){
                                                    dialogCargando.dismiss();
                                                    dialog.cancel();
                                                }else{
                                                    dialogCargando.dismiss();
                                                    Toast.makeText(view.getContext().getApplicationContext(), "Error creando el nuevo tipo de tarea", Toast.LENGTH_LONG).show();
                                                }

                                            } catch (JSONException e) {
                                                dialogCargando.dismiss();
                                                Toast.makeText(view.getContext().getApplicationContext(), "Error creando el nuevo tipo de tarea", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(view.getContext().getApplicationContext(), "Error en la conexion", Toast.LENGTH_LONG).show();
                                        }
                                    }
                            ){
                                /*
                                /**
                                 * Passing some request headers
                                 * */
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    HashMap<String, String> headers = new HashMap<String, String>();
                                    headers.put("Content-Type", "application/json; charset=utf-8");
                                    SharedPreferences SP = view.getContext().getSharedPreferences("TOKEN",MODE_PRIVATE);
                                    String tokenTemp = SP.getString("token","");
                                    headers.put("token", tokenTemp);
                                    return headers;
                                }
                            };
                            requestQueue.add(arrReq);
                        }
                    }
                });
            }
        });

        dialog.show();
    }
}
