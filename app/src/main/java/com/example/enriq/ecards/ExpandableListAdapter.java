package com.example.enriq.ecards;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by enriq on 22/01/2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<ItemListCheckbox>> listHashMap;


    ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<ItemListCheckbox>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.listHashMap.get(this.listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.listDataHeader.get(i);
    }

    @Override
    public ItemListCheckbox getChild(int i, int i1) {
        return this.listHashMap.get(this.listDataHeader.get(i)).get(i1); // i = Grupo, i1 = Hijo Item
    }

    public List<ItemListCheckbox> getChilds(int i) {
        return this.listHashMap.get(this.listDataHeader.get(i)); // i = Grupo, i1 = Hijo Item
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group, null);
        }
        TextView lblListHeader = (TextView) view.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return view;
    }

    @Override
    public View getChildView(int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        // Nombre
        ItemListCheckbox child = (ItemListCheckbox) getChild(i, i1);
        String nombre = child.getNombre();
        boolean check = child.isCheck();

        final int iTemp = i;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }
        TextView txtListChild = (TextView) view.findViewById(R.id.lblListItem);
        final CheckBox checkListChild = (CheckBox) view.findViewById(R.id.ListUsercheckbox);
        checkListChild.setChecked(check);
        txtListChild.setText(nombre);

        checkListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemListCheckbox objeto = getChild( iTemp,  i1);
                objeto.setCheck(checkListChild.isChecked());

            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
