package com.example.enriq.ecards;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ValentinaR on 26/01/2018.
 */

public class ExpanListAdapterCorte extends BaseExpandableListAdapter{

    private Context context;
    private List<String> listDataHeader;
    private String hora;
    private HashMap<String, List<String>> listHashMap;

    ExpanListAdapterCorte(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {return this.listDataHeader.size();}

    @Override
    public int getChildrenCount(int i) {
        return  this.listHashMap.get(this.listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.listHashMap.get(this.listDataHeader.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {return i;}

    @Override
    public long getChildId(int i, int i1) {return i1;}

    @Override
    public boolean hasStableIds() {return false;}

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group_corte, null);
        }
        TextView ListHeaderCorte = (TextView) view.findViewById(R.id.ListHeaderCorte);
        TextView HorasCorte = (TextView) view.findViewById(R.id.horascorte);
        ListHeaderCorte.setTypeface(null, Typeface.BOLD);
        ListHeaderCorte.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childText = (String) getChild(i, i1);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_corte, null);
        }
        TextView txtListChild = (TextView) view.findViewById(R.id.ListItemcorte);
        TextView HorasCorte = (TextView) view.findViewById(R.id.horascorte);
        txtListChild.setText(childText);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
