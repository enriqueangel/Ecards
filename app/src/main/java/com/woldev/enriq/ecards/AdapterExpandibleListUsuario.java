package com.woldev.enriq.ecards;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ValentinaR on 27/03/2018.
 */

public class AdapterExpandibleListUsuario extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<Usuario>> listHashMap;

    AdapterExpandibleListUsuario(Context context, List<String> listDataHeader, HashMap<String, List<Usuario>> listHashMap) {
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
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        Usuario child = (Usuario) getChild(i, i1);
        String image = child.getFoto();
        String nombre = child.getNombre();
        String horasl = child.getHorasl();
        String horast = child.getHorast();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.usuario_card, null);
        }
        TextView txtListChild = (TextView) view.findViewById(R.id.nombre);
        TextView HorasLaborales = (TextView) view.findViewById(R.id.campo_horas_laborales);
        TextView HorasTrabajadas = (TextView) view.findViewById(R.id.campo_horas_trabajadas);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageV);
        txtListChild.setText(nombre);
        HorasLaborales.setText(horasl);
        HorasTrabajadas.setText(horast);
        Picasso.with(context).load(image).into(imageView);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
