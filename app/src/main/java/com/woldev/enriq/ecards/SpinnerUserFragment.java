package com.woldev.enriq.ecards;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class SpinnerUserFragment extends Fragment {


    List<String> Usuarios = new ArrayList<>();
    ArrayAdapter<String> responAdapter;
    MaterialSpinner responsable;
    List<String> responItem = new ArrayList<>();

    JSONArray USUARIOS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_spinner_user, container, false);

        responsable = (MaterialSpinner) view.findViewById(R.id.encargado);

        try {
            USUARIOS = new JSONArray(getArguments().getString("USUARIOS"));
            cargarUsuarios();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        responAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, responItem);


        responAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        responsable.setAdapter(responAdapter);


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
        }
    }

    public boolean verificarCampos(){
        int Pos1 =  responsable.getSelectedItemPosition();


        if (Pos1 == 0){
            responsable.setError("Seleccione un usuario.");
            return false;
        }else{
            responsable.setError(null);
        }


        return true;
    }

    public String GetEncargadoID(){
        int Pos1 =  responsable.getSelectedItemPosition();
        return Usuarios.get(Pos1-1).toString();
    }

}
