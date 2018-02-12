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

    ExpandableListRadioAdapter listAdapter, listAdapterTester;
    ExpandableListView listView, listViewTester;
    List<String> listDataHeader, listDataHeaderTester;
    HashMap<String, List<ItemListCheckbox>> listDataChild, listDataChildTester;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_expandable_usuarios, container, false);

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listDataHeaderTester = new ArrayList<>();
        listDataChildTester = new HashMap<>();

        try {
            USUARIOS = new JSONArray(getArguments().getString("USUARIOS"));
            cargarUsuarios();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        listView = (ExpandableListView) view.findViewById(R.id.encargados);
        listViewTester = (ExpandableListView) view.findViewById(R.id.tester);

        // Datos de los usuarios tester
        listDataHeaderTester.add("Juegos");
        List<ItemListCheckbox> usuariosTemp = new ArrayList<>();
        usuariosTemp.add(new ItemListCheckbox("Enrique Angel", "aaa", true));
        usuariosTemp.add(new ItemListCheckbox("Enrique Angel", "aaa", false));
        usuariosTemp.add(new ItemListCheckbox("Enrique Angel", "aaa", false));
        listDataChildTester.put(listDataHeaderTester.get(0), usuariosTemp);

        listAdapter = new ExpandableListRadioAdapter(getActivity(), listDataHeader, listDataChild);
        listView.setAdapter(listAdapter);

        listAdapterTester = new ExpandableListRadioAdapter(getActivity(), listDataHeaderTester, listDataChildTester);
        listViewTester.setAdapter(listAdapterTester);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                setListViewHeight(expandableListView, i);
                return false;
            }
        });

        listViewTester.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                setListViewHeight(expandableListView, i);
                return false;
            }
        });

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

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListRadioAdapter listAdapter = (ExpandableListRadioAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }
}