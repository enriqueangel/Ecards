package com.example.enriq.ecards;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrearReunion extends AppCompatActivity {

    int day, month, year, hour, minute;

    String url;
    RequestQueue requestQueue;
    JSONArray Usuarios;

    TextView tv, tve;
    Calendar Date, Time;
    String format;
    Spinner mySpinner;
    Button CrearReunion;
    EditText Titulo, Lugar, Descripcion ,Hora, Fecha;
    TextInputLayout TILTitulo, TILLugar , TILHora, TILFecha;

    ExpandableListAdapter listAdapter;
    ExpandableListView listView;
    List<String> listDataHeader;
    HashMap<String, List<ItemListCheckbox>> listDataChild;

    String MetodoWS = "tarjetas/crear_reunion";

    AlertDialog dialog;



    private void CargarUsuarios() throws JSONException {

        url = getString(R.string.URLWS);
        url = url+"user/lista_rama";

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final AlertDialog dialog = mBuilder.create();

        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Usuarios = response.getJSONArray("users");
                            //Agrego cabecera principal
                            listDataHeader.add("Usuarios");

                            //Agrego cabecera de opciones
                            List<ItemListCheckbox> usuariosTemp = new ArrayList<>();

                            for (int i = 0; i < Usuarios.length(); i++) {
                                JSONObject row = Usuarios.getJSONObject(i);
                                String NombresTEmp = row.getString("nombres");
                                String ApellidosTEmp = row.getString("apellidos");
                                String NombreMostrar = NombresTEmp+" "+ApellidosTEmp;
                                String BDidTEmp = row.getString("_id");

                                usuariosTemp.add(new ItemListCheckbox(NombreMostrar, BDidTEmp, false));

                            }



                            listDataChild.put(listDataHeader.get(0), usuariosTemp);

                        } catch (JSONException e) {
                            Log.e("Volley", "Invalid JSON Object.");
                            Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.e("Volley", error.toString());
                        Toast.makeText(getApplicationContext(), "Error en la conexion", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            /*
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                SharedPreferences SP = getSharedPreferences("TOKEN",MODE_PRIVATE);
                String tokenTemp = SP.getString("token","");
                headers.put("token", tokenTemp);
                return headers;
            }
        };

        requestQueue.add(arrReq);

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_reunion);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Crear Reunion");

        listView = (ExpandableListView) findViewById(R.id.usuarios);
        try {
            CargarUsuarios();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        listView.setAdapter(listAdapter);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                setListViewHeight(expandableListView, i);
                return false;
            }
        });

        CrearReunion = (Button)  findViewById(R.id.BTNCrearRunion);
        Titulo= (EditText)  findViewById(R.id.EDTtitulo);
        Lugar= (EditText)  findViewById(R.id.EDTlugar);
        Descripcion= (EditText)  findViewById(R.id.EDTdescripcion);
        TILTitulo= (TextInputLayout)  findViewById(R.id.CampoTitulo);
        TILLugar= (TextInputLayout)  findViewById(R.id.CampoLugar);
        Hora= (EditText)  findViewById(R.id.EDThora);
        Fecha= (EditText)  findViewById(R.id.EDTfecha);

        TILHora= (TextInputLayout)  findViewById(R.id.CampoHora);
        TILFecha= (TextInputLayout)  findViewById(R.id.CampoFecha);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView2 = this.getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView2);
        dialog = mBuilder.create();



        CrearReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if  (!MetodosGlobales.validarCampoVacio(Titulo.getText().toString())){
                    TILTitulo.setError("Ingrese un Titulo");
                    //Toast.makeText(com.example.enriq.ecards.CrearReunion.this, "Ingrese un Titulo", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    TILTitulo.setError(null);
                }

                if  (!MetodosGlobales.validarCampoVacio(Fecha.getText().toString())){
                    TILFecha.setError("Ingrese una fecha");
                    //Toast.makeText(com.example.enriq.ecards.CrearReunion.this, "Ingrese un lugar" , Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    TILFecha.setError(null);
                }
                if  (!MetodosGlobales.validarCampoVacio(Hora.getText().toString())){
                    TILHora.setError("Ingrese una hora");
                    //Toast.makeText(com.example.enriq.ecards.CrearReunion.this, "Ingrese un lugar" , Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    TILHora.setError(null);
                }
                if  (!MetodosGlobales.validarCampoVacio(Lugar.getText().toString())){
                    TILLugar.setError("Ingrese un lugar");
                    //Toast.makeText(com.example.enriq.ecards.CrearReunion.this, "Ingrese un lugar" , Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    TILLugar.setError(null);
                }

                EnviarDatosWS();
            }
        });

        //Fecha
        Date = Calendar.getInstance();
        day = Date.get(Calendar.DAY_OF_MONTH);
        month = Date.get(Calendar.MONTH);
        year = Date.get(Calendar.YEAR);

        //tv.setText(day + "/" + month + "/" + year);
        Fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CrearReunion.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        String diaFormateada = (dayOfMonth < 10)? String.valueOf("0" + dayOfMonth) : String.valueOf(dayOfMonth);
                        String mesFormateada = (monthOfYear < 10)? String.valueOf("0" + monthOfYear) : String.valueOf(monthOfYear);
                        Fecha.setText(diaFormateada + "/" + mesFormateada + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //Hora
        Time = Calendar.getInstance();
        hour = Time.get(Calendar.HOUR_OF_DAY);
        minute = Time.get(Calendar.MINUTE);
        selectedTimeFormat(hour);
        //tve.setText(hour + " : " + minute + " " + format);
        Hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CrearReunion.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourOfDay = selectedTimeFormat(hourOfDay);
                        String horaFormateada = (hourOfDay < 10)? String.valueOf("0" + hourOfDay) : String.valueOf(hourOfDay);
                        String minutoFormateada = (minute < 10)? String.valueOf("0" + minute) : String.valueOf(minute);
                        Hora.setText(horaFormateada + ":" + minutoFormateada + " " + format);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
    }



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

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
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

    //Hora
    public int selectedTimeFormat(int hour){
        if (hour == 0){
            format = "AM";
            return hour += 12;
        } else if (hour == 12){
            format = "PM";
            return hour;
        } else if (hour > 12) {
            format = "PM";
            hour -= 12;
            return hour;
        } else {
            format = "AM";
            return hour;
        }
    }

    private void EnviarDatosWS(){
        ArrayList ListaUsuarios = GetUsuariosSeleccionados();

        if ( ListaUsuarios == null || !(ListaUsuarios.size() > 0) ){
            Toast.makeText(com.example.enriq.ecards.CrearReunion.this, "Debe seleccionar minimo un usuario" , Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.show();

        String url = getString(R.string.URLWS);
        url = url + MetodoWS;

        Map<String, String> params = new HashMap<String, String>();
        params.put("titulo", Titulo.getText().toString());
        params.put("fecha", Fecha.getText().toString());
        params.put("hora", Hora.getText().toString());
        params.put("lugar", Lugar.getText().toString());
        params.put("descripcion", Descripcion.getText().toString());
        params.put("Usuarios", ListaUsuarios.toString());

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respuesta = response.get("respuesta").toString();
                            if(respuesta.equals("si")){
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Reunion Creada.", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Error creando la reunion.", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            Log.e("Volley", "Invalid JSON Object.");
                            Toast.makeText(getApplicationContext(), "Error desconocido.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.e("Volley", error.toString());
                        Toast.makeText(getApplicationContext(), "Error en la conexion.", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            /*
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                SharedPreferences SP = getSharedPreferences("TOKEN",MODE_PRIVATE);
                String tokenTemp = SP.getString("token","");
                headers.put("token", tokenTemp);
                return headers;
            }
        };

        requestQueue.add(arrReq);

    }

    private ArrayList GetUsuariosSeleccionados() {
        List<ItemListCheckbox> ListaTemp =   listAdapter.getChilds(0);
        int tam = ListaTemp.size() ;
        ArrayList<String> ListaUsuarios = new ArrayList<String>();
        for (int i = 0; i < tam ; i++) {
            if (ListaTemp.get(i).isCheck()){
                ListaUsuarios.add(ListaTemp.get(i).getId());
            }

        }

        return  ListaUsuarios;
    }
}
