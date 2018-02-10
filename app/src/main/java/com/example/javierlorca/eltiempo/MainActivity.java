package com.example.javierlorca.eltiempo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

import java.util.List;

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

    SharedPreferences mPrefs;

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
        ListView listaprev=(ListView)findViewById(R.id.listaprev);
        //txvprueba=(TextView)findViewById(R.id.txvprueba);
        final TextView txverror=(TextView)findViewById(R.id.txverror);
        mPrefs=getPreferences(MODE_PRIVATE);

        final Context context=this;


        //array con los elementos que iran en la lista
        String[] values = new String[]{"day","min","max","eve","humidity","morn","speed", "description"};
        //adaptador para asignar la forma en que se mostraran los elementos
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        listaprev.setAdapter(adapter);//asignamos el adaptador al ListView creado



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
                loadData(context);
            }

        });


        //recupero los datos almacenados de PREVISIONES-----------------------
        Gson gsonrecuprev = new Gson();
        String jsonrecprev = mPrefs.getString("f", "");
        f = gsonrecuprev.fromJson(jsonrecprev, Forcast.class);
        if(w!=null){
            showData();
        }else{
            loadData(context);
        }

        FloatingActionButton fabprev = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(context);
            }

        });
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

        //COMENTARIO
    }//fin load data



    /**---------------------------------------INICIO SEGUNDO LOAD--------------------------------------------**/

    public void loadDataPrev(Context context){
        /**----------------------INICIO SEGUNDA API------------------------**/
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://samples.openweathermap.org/data/2.5/forecast/daily?id=524901&appid=b1b15e88fa797225412429c1c50c122a1";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject json= new JSONObject(response);
                            JSONObject list=json.getJSONObject("list");
                            JSONObject temp=json.getJSONObject("temp");
                            JSONObject humidity=json.getJSONObject("humidity");
                            JSONObject description=json.getJSONObject("description");
                            JSONObject speedprev=json.getJSONObject("speed");
                            double day = temp.getDouble("temp");//recibe la temperatura del dia
                            //String description=description.getString("description");//no es necesario
                            double eve=temp.getDouble("eve");
                            double morn=temp.getDouble("morn");
                            double temp_min=temp.getDouble("min");
                            double temp_max=temp.getDouble("max");
                            double humitidy=humidity.getDouble("humidity");
                            double speed=speedprev.getDouble("speed");
                            //String name=json.getString("name");
                            int code=json.getInt("cod");

                            f=new Forcast();
                            f.setDay(list.getDouble("day"));//day es temperatura del dia
                            f.setTemp_min(list.getDouble("min"));
                            f.setTemp_max(list.getDouble("max"));
                            f.setEve(list.getDouble("eve"));
                            f.setHumidity(list.getDouble("humidity"));
                            f.setMorn(list.getDouble("morn"));
                            f.setSpeed(list.getDouble("speed"));
                            f.setDescription(list.getString("description"));


                            //en JSON almaceno objetos
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
                            Gson gson= new Gson();
                            String jsonpref=gson.toJson(w);
                            prefsEditor.putString("f", jsonpref);
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

    public void showDataPrev(){


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