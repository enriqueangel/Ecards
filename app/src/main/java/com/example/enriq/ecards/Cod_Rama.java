package com.example.enriq.ecards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 *
 * create an instance of this fragment.
 */
public class Cod_Rama extends Fragment {

    Button BTNContinuar;
    EditText CodigoRAMA;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cod_rama, container, false);

        BTNContinuar  = (Button) view.findViewById(R.id.Continuar);
        CodigoRAMA = (EditText) view.findViewById(R.id.Codigo);


        BTNContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignUpFragment Fr1 = new SignUpFragment();

                Bundle args = new Bundle();
                args.putString("CODRAMA", CodigoRAMA.getText().toString());
                Fr1.setArguments(args);

                FragmentTransaction transition = getActivity().getSupportFragmentManager().beginTransaction();
                transition.replace(R.id.CONTENEDOR,Fr1);
                transition.commit();

            }
        });

        return view;
    }


}
