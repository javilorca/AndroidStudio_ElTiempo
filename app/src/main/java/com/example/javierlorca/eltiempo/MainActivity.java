package com.example.javierlorca.eltiempo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tvxdiasemana;
    TextView tvxhora;
    TextView tvxgrados;
    TextView tvxprevision;
    TextView txtviento;
    TextView txvprueba;
    TextView txttemp_max;
    TextView txttemp_min;
    TextView txverror;//TextView de pruebas
    ListView listaprev;
    TextView txprueba;
    SharedPreferences mPrefs;
    RecyclerView mRecyclerView;
    private ForcastAdapter adapter;

    Weather w;
    Forcast f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txverror=(TextView)findViewById(R.id.txverror);
        txttemp_max=(TextView)findViewById(R.id.txttemp_max);
        txttemp_min=(TextView)findViewById(R.id.txttemp_min);
        txtviento=(TextView)findViewById(R.id.txtviento);
        tvxgrados=(TextView)findViewById(R.id.txvgrados);
        tvxprevision=(TextView)findViewById(R.id.txvprevision);
        //ListView listaprev=(ListView)findViewById(R.id.listaprev);
        //txvprueba=(TextView)findViewById(R.id.txvprueba);
        final TextView txverror=(TextView)findViewById(R.id.txverror);
        mPrefs=getPreferences(MODE_PRIVATE);
        txprueba=(TextView)findViewById(R.id.txprueba);
        final Context context=this;
        //listaprev = (ListView)findViewById(R.id.listaprev);
        //mRecyclerView=(RecyclerView)findViewById(R.id.mRecyclerView);


        // Get a handle to the RecyclerView.
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        // Give the RecyclerView a default layout manager.



        //recupero los datos almacenados-------------------------------------
        Gson gsonrecu = new Gson();
        String jsonrec = mPrefs.getString("w", "");
        w = gsonrecu.fromJson(jsonrec, Weather.class);
        if(w!=null){
            showData();

        }else{
            loadData(context);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context,"Loading", Toast.LENGTH_SHORT).show();

                loadData(context);
                loadDataPrev(context);
            }

        });


        //recupero los datos almacenados de PREVISIONES-----------------------
        Gson gsonrecuprev = new Gson();
        String jsonrecprev = mPrefs.getString("f", "");
        f = gsonrecuprev.fromJson(jsonrecprev, Forcast.class);
        if(f!=null){
            showData();
        }else {
            loadData(context);
        }
    }

    public void loadData(Context context){
        /**----------------------INICIO API------------------------**/
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://api.openweathermap.org/data/2.5/weather?lat=3&lon=2&appid=530cfcd9c60a752f1128d67c5f11ecad";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject json= new JSONObject(response);
                            JSONObject main=json.getJSONObject("main");
                            JSONObject wind=json.getJSONObject("wind");
                            double temp = main.getDouble("temp");
                            double temp_max=main.getDouble("temp_max");
                            String name=json.getString("name");
                            int code=json.getInt("cod");

                            w= new Weather();
                            w.setTemp(main.getDouble("temp"));
                            w.setTemp_max(main.getDouble("temp_max"));
                            w.setTemp_min(main.getDouble("temp_min"));
                            w.setWind_speed(wind.getInt("speed"));
                            w.setWind_deg(wind.getInt("deg"));

                            //en JSON almaceno objetos
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
                            Gson gson= new Gson();
                            String jsonpref=gson.toJson(w);
                            prefsEditor.putString("w", jsonpref);
                            prefsEditor.commit();

                            showData();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txverror.setText(error.getMessage());
            }
            /**----------------------FIN API------------------------**/
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        Toast.makeText(context,"Loading", Toast.LENGTH_SHORT).show();
        //COMENTARIO
    }//fin load data



    /**---------------------------------------INICIO SEGUNDO LOAD--------------------------------------------**/

    public void loadDataPrev(Context context){
        /**----------------------INICIO SEGUNDA API------------------------**/
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://api.openweathermap.org/data/2.5/forecast?lat=3&lon=2&appid=530cfcd9c60a752f1128d67c5f11ecad";

        // Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject json= new JSONObject(response);
                            JSONArray list=json.getJSONArray("list");
                            Forcast item;
                            LinkedList<Forcast> listaForcast = new LinkedList<>();
                            for(int i=0;i<list.length();i++){
                                 item = new Forcast();
                                 JSONObject itemObject = list.getJSONObject(i);
                                 JSONObject main = itemObject.getJSONObject("main");
                                 item.setTemp(main.getDouble("temp"));
                                 item.setTemp_max(main.getDouble("temp_max"));
                                 item.setTemp_min(main.getDouble("temp_min"));
                                 item.setHumidity(main.getDouble("humidity"));

                                 JSONObject wind = itemObject.getJSONObject("wind");
                                 item.setSpeedprev(wind.getDouble("speed"));
                                 item.setDeg(wind.getDouble("deg"));

                                 DateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                 Date dt_txt = format.parse(itemObject.getString("dt_txt"));
                                 int dia = dt_txt.getDay();

                                 item.setDt_txt(dt_txt);//recibe el dia

                                 listaForcast.add(item);
                            }
                           showDataPrev(listaForcast);

                           //en JSON almaceno objetos
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
                            Gson gson= new Gson();
                            String jsonprefprev=gson.toJson(f);
                            prefsEditor.putString("f", jsonprefprev);
                            prefsEditor.commit();

                            showData();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txverror.setText(error.getMessage());
            }
            /**----------------------FIN SEGUNDA API------------------------**/
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        //COMENTARIO
    }//fin load data

    /**----------------------------FIN SEGUNDO LOAD-----------------------------**/




    public void showData(){
        //txvprueba.setText(""+w.getWind_deg());
        tvxgrados.setText(String.format("%.1f",w.toCelsius(w.getTemp())));//temperatura actual
        txtviento.setText(""+w.tokmhora(w.getWind_speed()));//velocidad del viento
        txttemp_max.setText(String.format("%.0f",w.toCelsius(w.getTemp_max())));//temperatura maxima
        txttemp_min.setText(String.format("%.0f",w.toCelsius(w.getTemp_min())));//temperatura minima

    }

    public void showDataPrev(LinkedList<Forcast> item){
       // txprueba.setText(""+item.getDt_txt());
      //  listaprev=(ListView)findViewById(R.id.listaprev);
        mRecyclerView=(RecyclerView)findViewById(R.id.mRecyclerView);

        ForcastAdapter adapter = new ForcastAdapter(getApplicationContext(), item);
        mRecyclerView.setAdapter(adapter);//asignamos el adaptador al RecyclerView creado


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //int size = ArrayAdapter();

        List<Forcast> prevision = new ArrayList<>();


        /**
        final String[] dia = new String[];
        //dia = ArrayAdapter();
        for(int i=0; i<size; i++){
            //Obtiene el campo Descripción y lo agrega al array de strings "zona".
            dia[i] = ArrayAdapter.get(i).getDescripcion();
        }
        zona[size - 1] = "TODOS"; // Se le resta 1 porque los indices de los arrays inician en 0
        **/

        //Toast toast = Toast.makeText(getApplicationContext(),"Toast prueba", Toast.LENGTH_SHORT);
        //toast.show();

    }

   /** @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }**/
}