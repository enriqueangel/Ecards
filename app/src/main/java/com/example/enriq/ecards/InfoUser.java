package com.example.enriq.ecards;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
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
                    fragment = new PerfilFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_desempeño:
                    toolbar.setTitle("Desempeño");
                    fragment = new DesempenoFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_tarjetas:
                    toolbar.setTitle("Tarjetas");
                    fragment = new TarjetasFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };
}
