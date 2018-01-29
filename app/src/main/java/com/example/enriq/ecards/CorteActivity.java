package com.example.enriq.ecards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CorteActivity extends AppCompatActivity {

    ExpanListAdapterCorte listAdapter;
    ExpandableListView listView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corte);

    // get the listview
    listView = (ExpandableListView) findViewById(R.id.ramas);

    // preparing list data
    prepareListData();

    listAdapter = new ExpanListAdapterCorte(this, listDataHeader, listDataChild);

    // setting list adapter
        listView.setAdapter(listAdapter);
}

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();


        // Adding child data
        listDataHeader.add("Movil");
        listDataHeader.add("Web");
        listDataHeader.add("Juegos");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("Valentina Rojas");
        top250.add("Enrique");
        top250.add("Ronal");
        top250.add("Laura");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("Laura");
        nowShowing.add("Daniela");


        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("1");
        comingSoon.add("2");
        comingSoon.add("3");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }


}
