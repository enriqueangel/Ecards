package com.example.enriq.ecards;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableUsuariosFragment extends Fragment {


    JSONArray USUARIOS;

    ExpandableListAdapter listAdapter;
    ExpandableListView listView;
    List<String> listDataHeader;
    HashMap<String, List<ItemListCheckbox>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_expandable_usuarios, container, false);

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        try {
            USUARIOS = new JSONArray(getArguments().getString("USUARIOS"));
            cargarUsuarios();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        listView = (ExpandableListView) view.findViewById(R.id.usuarios);

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        listView.setAdapter(listAdapter);

        return view;
    }

    private void cargarUsuarios()throws JSONException {

        List<ItemListCheckbox> usuariosTemp = new ArrayList<>();
        int contTemp = 0;
        String UltimaRama = "";

        for (int i = 0; i < USUARIOS.length(); i++) {
            JSONObject row = USUARIOS.getJSONObject(i);
            String NombresTEmp = row.getString("nombres");
            String ApellidosTEmp = row.getString("apellidos");
            String NombreMostrar = NombresTEmp+" "+ApellidosTEmp;
            String BDidTEmp = row.getString("_id");


            JSONArray RamaTemp = row.getJSONArray("areas");
            String AreaTemp = RamaTemp.getJSONObject(0).getString("nombre");


            if (i == 0){
                UltimaRama = AreaTemp;
                listDataHeader.add(UltimaRama);
            }else{
                if (!UltimaRama.equals(AreaTemp)){
                    contTemp++;
                    listDataHeader.add(UltimaRama);
                    listDataChild.put(listDataHeader.get(contTemp), usuariosTemp);
                    usuariosTemp.clear();
                }
            }

            usuariosTemp.add(new ItemListCheckbox(NombreMostrar, BDidTEmp, false));

        }


    }
}