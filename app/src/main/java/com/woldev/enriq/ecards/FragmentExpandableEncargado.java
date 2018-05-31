package com.woldev.enriq.ecards;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FragmentExpandableEncargado extends Fragment {

    AdapterExpandableListRadio listAdapterTester;
    ExpandableListView listViewTester;
    List<String> listDataHeaderTester;
    HashMap<String, List<ItemListCheckbox>> listDataChildTester;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_expandable_tester, container, false);

        listDataHeaderTester = new ArrayList<>();
        listDataChildTester = new HashMap<>();

        listViewTester = (ExpandableListView) view.findViewById(R.id.tester);

        listDataHeaderTester.add("Juegos");
        List<ItemListCheckbox> usuariosTemp = new ArrayList<>();
        usuariosTemp.add(new ItemListCheckbox("Enrique Angel", "aaa", false));
        usuariosTemp.add(new ItemListCheckbox("Enrique Angel", "aaa", false));
        usuariosTemp.add(new ItemListCheckbox("Enrique Angel", "aaa", false));
        listDataChildTester.put(listDataHeaderTester.get(0), usuariosTemp);

        listAdapterTester = new AdapterExpandableListRadio(getActivity(), listDataHeaderTester, listDataChildTester);
        listViewTester.setAdapter(listAdapterTester);

        listViewTester.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                setListViewHeight(expandableListView, i);
                return false;
            }
        });

        return view;
    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        AdapterExpandableListRadio listAdapter = (AdapterExpandableListRadio) listView.getExpandableListAdapter();
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
