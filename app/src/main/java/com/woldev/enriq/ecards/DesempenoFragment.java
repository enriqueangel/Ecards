package com.woldev.enriq.ecards;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.woldev.enriq.ecards.R.id.*;

public class DesempenoFragment extends Fragment{

    ExpanListAdapterDesem listAdapter;
    ExpandableListView listView;
    List<String> listDataHeader;
    HashMap<String, List<UsuarioDesempeno>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_desempeno, container, false);

        listView = (ExpandableListView) view.findViewById(desempeno);
        prepareListData();
        listAdapter = new ExpanListAdapterDesem(getActivity(),listDataHeader,listDataChild);
        listView.setAdapter(listAdapter);
        
        
        return view;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        listDataHeader.add("Fecha1");
        listDataHeader.add("Fecha2");

        // Adding child data
        List<UsuarioDesempeno> top250 = new ArrayList<>();
        top250.add(new UsuarioDesempeno("Valentina Rojas", "18:30", "eshbdvhhfhfhfffhfbfehhfbbdhvbdbvvbhvbvvbhvvvbjch hcvhdv dhbcsjbbbdhjdbdbdhdhbdbddb"));
        top250.add(new UsuarioDesempeno("Enrique Angel", "00:00","ggfhh"));
        top250.add(new UsuarioDesempeno("Ronal Gonzales", "03:00","ttuii"));
        top250.add(new UsuarioDesempeno("Laura Gonzales", "12:45","yg"));

        List<UsuarioDesempeno> nowShowing = new ArrayList<>();
        nowShowing.add(new UsuarioDesempeno("Laura Galenano", "10:00","gyfbhfdbfdjkdsfhvfdfgufvffffg"));
        nowShowing.add(new UsuarioDesempeno("Daniela Ramirez", "08:00","098"));

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
    }
}
