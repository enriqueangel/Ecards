package com.woldev.enriq.ecards;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.woldev.enriq.ecards.R.id.*;

public class FragmentDesempeno extends Fragment{
    ExpanListAdapterDesem listAdapter;
    ExpandableListView listView;
    List<String> listDataHeader;
    HashMap<String, List<Desempeno>> listDataChild;
    String IDusuario;

    String url;
    RequestQueue requestQueue;
    AlertDialog dialog;

    JSONArray Datos;

    @Override
    public void onStart() {
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_progress, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();

        String urlTemp = getString(R.string.URLWS);
        urlTemp = urlTemp+"trabajo/desempeno";
        dialog.show();

        Map<String, String> params = new HashMap<String, String>();
        params.put("id", IDusuario);

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, urlTemp, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Datos = response.getJSONArray("trabajos");
                            cargarTrabajos();
                        } catch (JSONException e) {
                            Log.e("Volley", "Invalid JSON Object.");
                            Toast.makeText(getActivity().getApplicationContext(), "Error cargando la informacion.", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(view, "Error desconocido.", Snackbar.LENGTH_SHORT).show();
                        } catch (ParseException e) {
                            Toast.makeText(getActivity().getApplicationContext(), "Error cargando la informacion.", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        dialog.dismiss();
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

        super.onStart();
    }

    private void cargarTrabajos() throws JSONException, ParseException {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        Boolean IsFirst = true;
        String Ultifecha = "";

        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");

        List<Desempeno> ListaMostrar = new ArrayList<>();

        int ContTemp = 0 ;

        for (int i = 0; i < Datos.length(); i++) {
            JSONObject row = Datos.getJSONObject(i);

            String NombreTareaTemp = "";

            try{
                JSONObject tarjeta = row.getJSONObject("tarjeta");
                NombreTareaTemp = tarjeta.getString("titulo");
            }catch (JSONException ignored) {

            }

            try{
                JSONObject tester = row.getJSONObject("tester");
                NombreTareaTemp = tester.getString("titulo");
            }catch (JSONException ignored) {

            }

            try{
                JSONObject reunion = row.getJSONObject("reunion");
                NombreTareaTemp = reunion.getString("titulo");
            }catch (JSONException ignored) {

            }

            Date Fecha = format2.parse(row.getString("fecha"));
            String FechaString = parseador.format(Fecha);

            if (IsFirst){
                IsFirst = false;
                Ultifecha = FechaString;
            }else{
                if(!Ultifecha.equals(FechaString)){
                    // Adding child data
                    listDataHeader.add(Ultifecha);
                    listDataChild.put(listDataHeader.get(ContTemp), ListaMostrar); // Header, Child data
                    ContTemp++;
                    ListaMostrar = new ArrayList<>();
                    Ultifecha = FechaString;
                }
            }
            String DescripcionTemp = row.getString("descripcion");
            String TiempoTemp = row.getString("horas_trabajadas");

            ListaMostrar.add(new Desempeno(NombreTareaTemp, TiempoTemp, DescripcionTemp));
        }

        listDataHeader.add(Ultifecha);
        listDataChild.put(listDataHeader.get(ContTemp), ListaMostrar);

        listAdapter = new ExpanListAdapterDesem(getActivity(),listDataHeader,listDataChild);
        listView.setAdapter(listAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_desempeno, container, false);
        IDusuario = getArguments().getString("ID");
        listView = (ExpandableListView) view.findViewById(desempeno);
        return view;
    }
}
