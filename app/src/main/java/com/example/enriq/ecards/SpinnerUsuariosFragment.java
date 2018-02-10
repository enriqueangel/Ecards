package com.example.enriq.ecards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class SpinnerUsuariosFragment extends Fragment {

    List<String> Usuarios = new ArrayList<>();

    ArrayAdapter<String> responAdapter, testerAdapter;
    MaterialSpinner responsable, tester;
    List<String> responItem = new ArrayList<>();
    List<String> testerItem = new ArrayList<>();

    JSONArray USUARIOS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_spinner_usuarios, container, false);

        responsable = (MaterialSpinner) view.findViewById(R.id.encargado);
        tester = (MaterialSpinner) view.findViewById(R.id.tester);

        try {
            USUARIOS = new JSONArray(getArguments().getString("USUARIOS"));
            cargarUsuarios();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        responAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, responItem);
        testerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, testerItem);

        responAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        responsable.setAdapter(responAdapter);
        tester.setAdapter(testerAdapter);



        return view;
    }

    private void cargarUsuarios() throws JSONException {
        for (int i = 0; i < USUARIOS.length(); i++) {
            JSONObject row = USUARIOS.getJSONObject(i);
            String NombresTEmp = row.getString("nombres");
            String ApellidosTEmp = row.getString("apellidos");
            String NombreMostrar = NombresTEmp+" "+ApellidosTEmp;
            String BDidTEmp = row.getString("_id");
            Usuarios.add(BDidTEmp);
            responItem.add(NombreMostrar);
            testerItem.add(NombreMostrar);
        }
    }
}

