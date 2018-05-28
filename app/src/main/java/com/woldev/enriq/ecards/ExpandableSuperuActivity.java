package com.woldev.enriq.ecards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableSuperuActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapterSuperu listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_superu);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Usuarios");


        listView = (ExpandableListView) findViewById(R.id.usuarios);
        initData();
        listAdapter = new ExpandableListAdapterSuperu(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Movil");
        listDataHeader.add("Web");

        List<String> movil = new ArrayList<>();
        movil.add("Valentina Rojas");
        movil.add("Laura Gonzalez");
        movil.add("Enrique Angel");
        movil.add("Ronald Gonzalez");

        List<String> web = new ArrayList<>();
        web.add("Daniela Jaramillo");
        web.add("Laura Galeano");
        web.add("Jenifer Ramirez");

        listHash.put(listDataHeader.get(0),movil);
        listHash.put(listDataHeader.get(1),web);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent i;
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
