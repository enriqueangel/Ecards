package com.woldev.enriq.ecards;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableUserFragment extends Fragment {

    JSONArray USUARIOS;

    ExpandableListRadioAdapter listAdapter;

    List<String> listDataHeader;

    HashMap<String, List<ItemListCheckbox>> listDataChild;

    ExpandableListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expandable_user, container, false);

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();


        try {
            USUARIOS = new JSONArray(getArguments().getString("USUARIOS"));
            cargarUsuarios();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView = (ExpandableListView) view.findViewById(R.id.encargados);

        listAdapter = new ExpandableListRadioAdapter(getActivity(), listDataHeader, listDataChild);
        listView.setAdapter(listAdapter);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                setListViewHeight(expandableListView, i);
                return false;
            }
        });


        return view;
    }

    private void cargarUsuarios() throws JSONException {

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
                    UltimaRama = AreaTemp;
                    listDataChild.put(listDataHeader.get(contTemp), usuariosTemp);

                    contTemp++;
                    listDataHeader.add(UltimaRama);

                    usuariosTemp = new ArrayList<>();

                }
            }

            usuariosTemp.add(new ItemListCheckbox(NombreMostrar, BDidTEmp, false));


        }

        listDataChild.put(listDataHeader.get(contTemp), usuariosTemp);


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