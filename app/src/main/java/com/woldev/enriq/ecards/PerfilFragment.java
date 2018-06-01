package com.woldev.enriq.ecards;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilFragment extends Fragment {

    JSONObject DATOS;
    CircleImageView imagen;
    TextView Nombre,Correo,Telefono,Ramas,HorasContratadas;
    FloatingActionButton btnEditar;
    Context context;

    String tipoUsuario = "LIDER";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_perfil, container, false);

        LinearLayout cabecera = (LinearLayout) view.findViewById(R.id.cabecera);
        cabecera.setBackground(getResources().getDrawable(R.drawable.fondo_perfil));

        btnEditar = (FloatingActionButton) view.findViewById(R.id.btnEditar);

        Nombre = (TextView) view.findViewById(R.id.TXVnombre);
        Correo = (TextView) view.findViewById(R.id.TXVCorreo);
        Telefono = (TextView) view.findViewById(R.id.Telefono);
        Ramas = (TextView) view.findViewById(R.id.TXVRAMAS);
        HorasContratadas = (TextView) view.findViewById(R.id.Horas_Contratadas);
        imagen = (CircleImageView) view.findViewById(R.id.imagen);

        if(tipoUsuario.equals("SUPERU")){
            btnEditar.setVisibility(View.VISIBLE);
        }

        try {
            DATOS = new JSONObject(getArguments().getString("DATOS"));

            String NombreTemp = DATOS.getString("nombres");
            String ApellidosTemp = DATOS.getString("apellidos");

            Nombre.setText(NombreTemp+" "+ApellidosTemp);
            Correo.setText(DATOS.getString("email"));
            Telefono.setText(DATOS.getString("telefono"));
            Ramas.setText(GetRamas(DATOS.getJSONArray("areas")));
            HorasContratadas.setText(DATOS.getString("horas_contratadas"));

            Picasso.with(context).load(DATOS.getString("foto")).error(R.drawable.ic_perfilsinfoto).into(imagen);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    private String GetRamas(JSONArray jsonArray) throws JSONException {
        String Ramas = "";
        Boolean Primero = true;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject Rama = jsonArray.getJSONObject(i);
            if (!Primero){
                Ramas += ", ";
            };

            Ramas += Rama.getString("nombre");
            Primero = false;
        }
        return Ramas;
    };

}
