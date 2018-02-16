package com.example.enriq.ecards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class ExpandableUserFragment extends Fragment {

    ExpandableListRadioAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expandable_user, container, false);
        return view;
    }

    public boolean verificarCampos(){

        String EncargadoIDtemp = "-1";


        int NumRamas = listAdapter.getGroupCount();
        for (int i = 0; i < NumRamas ; i++) {

            List<ItemListCheckbox> ListaEncargado =   listAdapter.getChilds(i);


            int tam = ListaEncargado.size() ;
            for (int i2 = 0; i2 < tam ; i2++) {
                if (ListaEncargado.get(i2).isCheck()){
                    EncargadoIDtemp = ListaEncargado.get(i2).getId();
                }

            }


        }

        if (EncargadoIDtemp.equals("-1")){
            Toast.makeText(getActivity(), "Seleccione un encargado.", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;

    }

    public String GetEncargadoID(){
        String EncargadoIDtemp = "-1";

        int NumRamas = listAdapter.getGroupCount();
        for (int i = 0; i < NumRamas ; i++) {

            List<ItemListCheckbox> ListaEncargado =   listAdapter.getChilds(i);


            int tam = ListaEncargado.size() ;
            for (int i2 = 0; i2 < tam ; i2++) {
                if (ListaEncargado.get(i2).isCheck()){
                    EncargadoIDtemp = ListaEncargado.get(i2).getId();
                }


            }
        }

        return EncargadoIDtemp;
    }

}