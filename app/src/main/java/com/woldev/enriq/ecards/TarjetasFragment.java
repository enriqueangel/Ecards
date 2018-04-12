package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class TarjetasFragment extends Fragment {

    RecyclerView contenedor;
    ArrayList<Fuente> listaTarjetas = new ArrayList<Fuente>();

    String url;
    RequestQueue requestQueue;

    AlertDialog dialog;

    JSONArray TARJETAS,Reuniones,Testers;

    Date FechaServidor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tarjetas, container, false);

        url = getString(R.string.URLWS);
        url = url+"tarjetas";

        contenedor = (RecyclerView) view.findViewById(R.id.contenedor);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();

        dialog.show();

        Map<String, String> params = new HashMap<String, String>();

        String IDusuario = getArguments().getString("ID");

        params.put("id", IDusuario);

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            TARJETAS = response.getJSONArray("tarjetas");
                            Reuniones = response.getJSONArray("reunion");
                            Testers = response.getJSONArray("tester");
                            String dtStart = response.getString("fecha");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                            try {
                                FechaServidor = format.parse(dtStart);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            ImprimirReuniones();
                            ImprimirTesters();
                            ImprimirTargetas();


                            contenedor.setHasFixedSize(true);// no va a presentar variables en cuanto al tamaño
                            RelativeLayout layout = new RelativeLayout(getActivity().getApplicationContext());
                            layout.setVerticalGravity(RelativeLayout.CENTER_VERTICAL);

                            //INDICO CUAL TARJETA QUIERO MOSTRAR, PENDIENTE:PROGRAMAR LA ESCOGENCIA DE LA TARJETA
                            contenedor.setAdapter(new Adaptador(listaTarjetas));

                            contenedor.setLayoutManager(new LinearLayoutManager(getActivity()));



                            dialog.dismiss();
                        } catch (JSONException e) {
                            Log.e("Volley", "Invalid JSON Object.");
                            dialog.dismiss();
                            Toast.makeText(getActivity().getApplicationContext(), "Error cargando las tarjetas.", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(view, "Error desconocido.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.e("Volley", error.toString());
                        Toast.makeText(getActivity().getApplicationContext(), "Error en la conexion.", Toast.LENGTH_SHORT).show();
                        // Snackbar.make(view, "Error en la conexion.", Snackbar.LENGTH_SHORT).show();
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
                SharedPreferences SP = getActivity().getSharedPreferences("TOKEN",MODE_PRIVATE);
                String tokenTemp = SP.getString("token","");
                headers.put("token", tokenTemp);
                return headers;
            }
        };

        requestQueue.add(arrReq);

        return view;
    }

    private void ImprimirTargetas() throws JSONException {
        for (int i = 0; i < TARJETAS.length(); i++) {
            JSONObject row = TARJETAS.getJSONObject(i);
            String DescripcionTEMP = row.getString("titulo");

            String TipoTEMP;
            String VersionTEMP;

            String TiempoRealizado = row.getString("tiempo_trabajado");
            String dtStart2 = row.getString("fecha_entrega");
            String dtStart = "";
            Date FechaEntrega = null ;

            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");

            try {
                FechaEntrega = format2.parse(dtStart2);
                dtStart = parseador.format(FechaEntrega);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int ColorTArgeta ;
            String COLORTEMP = "";

            if (row.getString("tipo").equals("2")){
                TipoTEMP = "";
                VersionTEMP = "1";
                ColorTArgeta  = R.drawable.ic_card_purple;
                COLORTEMP = "morado";
                //ContCardsVerdeint ++;
            }else{
                JSONObject TipoTareaTEMP = row.getJSONObject("tipotarea");
                TipoTEMP = TipoTareaTEMP.getString("tipo");
                VersionTEMP = row.getString("version");

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                long DiasRestantes = 100;
                DiasRestantes = calcularColor(FechaEntrega);

                if (DiasRestantes <= 0){

                    ColorTArgeta  = R.drawable.ic_card_red;
                    COLORTEMP = "rojo";
                }else if(DiasRestantes <= 2){

                    ColorTArgeta  = R.drawable.ic_card_red;
                    COLORTEMP = "rojo";
                }else if(DiasRestantes <= 4){

                    ColorTArgeta  = R.drawable.ic_card_yellow;
                    COLORTEMP = "naranja";
                }else{

                    ColorTArgeta  = R.drawable.ic_card_white;
                    COLORTEMP = "blanco";
                }
            }
            row.put("Color",COLORTEMP);

            listaTarjetas.add(new Fuente(DescripcionTEMP,TipoTEMP,dtStart,TiempoRealizado,VersionTEMP,ColorTArgeta,false,row, false));
        }
    };

    private void ImprimirTesters() throws JSONException {
        for (int i = 0; i < Testers.length(); i++) {
            JSONObject row = Testers.getJSONObject(i);

            String DescripcionTEMP = "Tester";

            String TipoTEMP;
            String VersionTEMP;

            String TiempoRealizado = row.getString("tiempo_trabajado");
            String dtStart2 = row.getString("fecha_entrega");
            String dtStart = "";
            Date FechaEntrega = null ;

            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");

            try {
                FechaEntrega = format2.parse(dtStart2);
                dtStart = parseador.format(FechaEntrega);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int ColorTArgeta ;
            String COLORTEMP = "";

            TipoTEMP = "";
            VersionTEMP = "1";

            long DiasRestantes = 100;
            DiasRestantes = calcularColor(FechaEntrega);

            if (DiasRestantes <= 0){

                ColorTArgeta  = R.drawable.ic_card_red;
                COLORTEMP = "verde-rojo";
            }else if(DiasRestantes <= 2){

                ColorTArgeta  = R.drawable.ic_card_red;
                COLORTEMP = "verde-rojo";
            }else if(DiasRestantes <= 4){

                ColorTArgeta  = R.drawable.ic_card_yellow;
                COLORTEMP = "verde-naranja";
            }else{

                ColorTArgeta  = R.drawable.ic_card_white;
                COLORTEMP = "verde-blanco";
            }

            row.put("Color",COLORTEMP);

            listaTarjetas.add(new Fuente(DescripcionTEMP,TipoTEMP,dtStart,TiempoRealizado,VersionTEMP,ColorTArgeta,false,row, false));



        }
    }

    private void ImprimirReuniones() throws JSONException {
        for (int i = 0; i < Reuniones.length(); i++) {
            JSONObject row = Reuniones.getJSONObject(i);
            String DescripcionTEMP = row.getString("titulo");
            String TipoTEMP = row.getString("hora");
            String VersionTEMP = "1";
            String TiempoEsperado = row.getString("lugar");
            String dtStart2 = row.getString("fecha");
            String dtStart = "";

            // el que parsea
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            // el que formatea
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

            Date date = null;
            try {
                date = parseador.parse(dtStart2);
                dtStart = formateador.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int ColorTArgeta;
            String COLORTEMP = "azul";
            ColorTArgeta = R.drawable.card_indigo;
            row.put("Color", COLORTEMP);

            listaTarjetas.add(new Fuente(DescripcionTEMP, TipoTEMP, dtStart, TiempoEsperado, VersionTEMP, ColorTArgeta, true, row, false));
        }
    }

    private long calcularColor(Date FechaFin){
        Date fechaInicio = FechaServidor;
        Date fechaLlegada = FechaFin;

        // tomamos la instancia del tipo de calendario
        Calendar calendarInicio = Calendar.getInstance();
        Calendar calendarFinal = Calendar.getInstance();

        // Configramos la fecha del calendatio, tomando los valores del date que
        // generamos en el parse
        calendarInicio.setTime(fechaInicio);
        calendarFinal.setTime(fechaLlegada);

        // obtenemos el valor de las fechas en milisegundos
        long milisegundos1 = calendarInicio.getTimeInMillis();
        long milisegundos2 = calendarFinal.getTimeInMillis();

        // tomamos la diferencia
        long diferenciaMilisegundos = milisegundos2 - milisegundos1;

        // Despues va a depender en que formato queremos  mostrar esa
        // diferencia, minutos, segundo horas, dias, etc, aca van algunos
        // ejemplos de conversion

        // calcular la diferencia en segundos
        long diffSegundos =  Math.abs (diferenciaMilisegundos / 1000);

        // calcular la diferencia en minutos
        long diffMinutos =  Math.abs (diferenciaMilisegundos / (60 * 1000));
        long restominutos = diffMinutos%60;

        // calcular la diferencia en horas
        long diffHoras =   (diferenciaMilisegundos / (60 * 60 * 1000));

        // calcular la diferencia en dias
        long diffdias = diferenciaMilisegundos / (24 * 60 * 60 * 1000) ;

        // devolvemos el resultado
        return diffdias;
    }

}
