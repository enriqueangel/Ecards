package com.example.enriq.ecards;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class InfoUser extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

        toolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle("Perfil");
        loadFragment(new PerfilFragment());
    }

    private void loadFragment(Fragment fragment) {

        //Paso 1: Obtener la instancia del administrador de fragmentos
        FragmentManager fragmentManager = getFragmentManager();

        //Paso 2: Crear una nueva transacción
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //Paso 3: Crear un nuevo fragmento y añadirlo

        transaction.replace(R.id.InfUserCONTENEDOR, fragment);


        //Paso 4: Confirmar el cambio
        transaction.commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_perfil:
                    toolbar.setTitle("Perfil");

                    PerfilFragment Fr1 = new PerfilFragment();
                    loadFragment(Fr1);
                    return true;

                case R.id.navigation_desempeño:
                    toolbar.setTitle("Desempeño");
                    DesempenoFragment Fr2 = new DesempenoFragment();
                    loadFragment(Fr2);
                    return true;
                case R.id.navigation_tarjetas:
                    toolbar.setTitle("Tarjetas");

                    TarjetasFragment Fr3 = new TarjetasFragment();
                    loadFragment(Fr3);
                    return true;
            }

            return false;
        }
    };
}
