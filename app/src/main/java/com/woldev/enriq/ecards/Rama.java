package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class Rama extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rama);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Rama");

        btnEditar = (FloatingActionButton) findViewById(R.id.BTNEditarinformacion);
        btnEditar.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void crearDialogEditRama() {
        final AlertDialog.Builder EditRama = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        final View mView = this.getLayoutInflater().inflate(R.layout.dialog_edit_rama, null);
        EditRama.setView(mView);
        EditRama.setTitle("Editar Rama");

        final TextView Nombrerama = (TextView) mView.findViewById(R.id.EDTnombrer);
        final TextView Descripcion = (TextView) mView.findViewById(R.id.EDTcodigo);
        final TextInputLayout campoHoras = (TextInputLayout) mView.findViewById(R.id.CamponombreRama);
        final TextInputLayout campoDescripcion = (TextInputLayout) mView.findViewById(R.id.Campocodigo);

        EditRama.setPositiveButton("Editar", null);

        EditRama.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = EditRama.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Editar", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        dialog.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BTNEditarinformacion:
                crearDialogEditRama();
        }
    }
}
