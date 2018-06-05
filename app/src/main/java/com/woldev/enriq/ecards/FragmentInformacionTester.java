package com.woldev.enriq.ecards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentInformacionTester extends Fragment {

    JSONObject DATOS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tester, container, false);

        try {
            DATOS = new JSONObject(getArguments().getString("DATOS"));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return  v;
    }
}
