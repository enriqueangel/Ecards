package com.woldev.enriq.ecards;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class FragmentSpinnerUsuarios extends Fragment {

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


    public boolean verificarCampos(){
        int Pos1 =  responsable.getSelectedItemPosition();
        int Pos2 =  tester.getSelectedItemPosition();

        if (Pos1 == 0){
            responsable.setError("Seleccione un usuario.");
            return false;
        }else{
            responsable.setError(null);
        }

        if (Pos2 == 0){
            tester.setError("Seleccione un usuario.");
            return false;
        }else{
            tester.setError(null);
        }

        if (Pos1 == Pos2){
            Toast.makeText(getActivity(), "El responsable y el tester tienen que ser diferentes.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public String GetEncargadoID(){
        int Pos1 =  responsable.getSelectedItemPosition();
        return Usuarios.get(Pos1-1).toString();
    }

    public String GetTesterID(){
        int Pos2 =  tester.getSelectedItemPosition();
        return Usuarios.get(Pos2-1).toString();
    }

}

