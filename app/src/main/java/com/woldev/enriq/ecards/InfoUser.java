package com.woldev.enriq.ecards;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

public class InfoUser extends AppCompatActivity {

    JSONObject DATOS;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

        toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Perfil");

        try {
            DATOS = new JSONObject(getIntent().getStringExtra("DATOS"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        PerfilFragment Fr1 = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString("DATOS", DATOS.toString());
        Fr1.setArguments(args);
        loadFragment(Fr1);
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
            switch (item.getItemId()) {
                case R.id.navigation_perfil:
                    toolbar.setTitle("Perfil");
                    PerfilFragment Fr1 = new PerfilFragment();

                    Bundle args = new Bundle();
                    args.putString("DATOS", DATOS.toString());
                    Fr1.setArguments(args);
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

                    Bundle args2 = new Bundle();
                    try {
                        args2.putString("ID", DATOS.getString("_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Fr3.setArguments(args2);
                    loadFragment(Fr3);
                    return true;
            }

            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                //Intent i = new Intent(CrearReunion.this, MenuAdmin.class);
                //startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
