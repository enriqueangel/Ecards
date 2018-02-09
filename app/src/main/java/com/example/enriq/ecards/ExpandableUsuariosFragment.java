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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableUsuariosFragment extends Fragment {

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

        listDataHeader.add("Movil");
        listDataHeader.add("Juegos");

        List<ItemListCheckbox> usuariosTemp = new ArrayList<>();
        usuariosTemp.add(new ItemListCheckbox("Enrique Angel", "aaaa", false));
        usuariosTemp.add(new ItemListCheckbox("Valentina Rojas", "aaaa", false));
        usuariosTemp.add(new ItemListCheckbox("Ronald Gonzales", "aaaa", false));

        List<ItemListCheckbox> usuariosTemp2 = new ArrayList<>();
        usuariosTemp2.add(new ItemListCheckbox("Enrique Angel", "aaaa", false));
        usuariosTemp2.add(new ItemListCheckbox("Valentina Rojas", "aaaa", false));
        usuariosTemp2.add(new ItemListCheckbox("Ronald Gonzales", "aaaa", false));

        listDataChild.put(listDataHeader.get(0), usuariosTemp);
        listDataChild.put(listDataHeader.get(1), usuariosTemp2);

        listView = (ExpandableListView) view.findViewById(R.id.usuarios);

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        listView.setAdapter(listAdapter);

        return view;
    }
}